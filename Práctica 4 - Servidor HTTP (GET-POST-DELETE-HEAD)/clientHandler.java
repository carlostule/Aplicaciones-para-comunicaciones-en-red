import java.net.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;

public class clientHandler implements Runnable{
	final Socket client;
	final DataInputStream input;
	final DataOutputStream output;
	final int port;
	final InetAddress addr;

	private int bufferSize = 4096;

	private Pattern requestPattern = Pattern.compile("(GET|POST|PUT|HEAD|DELETE)(\\s+)(\\/.*)?(\\s+)(HTTP\\/\\d\\.\\d)");
	private Pattern headerPattern = Pattern.compile("([\\w-]+): (.*)");
	private Pattern getPostPattern = Pattern.compile("<\\?__(GET|POST)\\[\"(.*?)\"\\]\\?>");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	private String user = "carlostule";
	private String pass = "password";

	private HashMap<String, Boolean> logged = new HashMap<>();

	public clientHandler(Socket client) throws Exception{
		this.client = client;
		this.input = new DataInputStream(client.getInputStream());
		this.output = new DataOutputStream(client.getOutputStream());
		this.addr = client.getInetAddress();
		this.port = client.getPort();
	}

	private void answer(boolean sendBody, int statusCode, String statusText, HashMap<String, String> responseHeaders, DataInputStream responseBody, long length) throws Exception{
		output.writeBytes("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		if(responseHeaders == null){
			responseHeaders = new HashMap<>();
		}
		responseHeaders.put("Content-Length", String.valueOf(length));
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		responseHeaders.put("Date", dateFormat.format(new Date()));
		responseHeaders.forEach((key, value) -> {
			try{
				output.writeBytes(key + ": " + value + "\r\n");
			}catch(Exception e){}
		});
		output.writeBytes("\r\n");
		if(sendBody && responseBody != null){
			byte[] buffer = new byte[bufferSize];
			int read = 0;
			while((read = responseBody.read(buffer)) > 0){
				output.write(buffer, 0, read);
			}
		}
	}

	private void answer(boolean sendBody, int statusCode, String statusText, HashMap<String, String> responseHeaders, String text) throws Exception{
		if(responseHeaders == null){
			responseHeaders = new HashMap<>();
		}
		responseHeaders.put("Content-Type", "text/html; charset=utf-8");
		byte[] bytes = text.getBytes("UTF-8");
		DataInputStream responseBody = new DataInputStream(new ByteArrayInputStream(bytes));
		answer(sendBody, statusCode, statusText, responseHeaders, responseBody, bytes.length);
		responseBody.close();
	}

	private void answer(boolean sendBody, int statusCode, String statusText, HashMap<String, String> responseHeaders, File file, HashMap<String, String> getMap, HashMap<String, String> postMap) throws Exception{
		if(responseHeaders == null){
			responseHeaders = new HashMap<>();
		}
		int pos = file.getName().lastIndexOf('.');
		String ext = "";
		if(pos > 0){
			ext = file.getName().substring(pos + 1).toLowerCase();
			responseHeaders.put("Content-Type", mime.get(ext));
		}
		if(ext.equals("html") || ext.equals("htm")){
			String entireHTML = new Scanner(file, "UTF-8").useDelimiter("\\A").next();
			Matcher m = getPostPattern.matcher(entireHTML);
			StringBuffer buf = new StringBuffer();
			while(m.find()){
				String type = m.group(1);
				String key = m.group(2);
				String value = "";
				if(type.equals("GET") && getMap != null && getMap.containsKey(key)) value = getMap.get(key);
				else if(type.equals("POST") && postMap != null && postMap.containsKey(key)) value = postMap.get(key);
				m.appendReplacement(buf, value);
			}
			m.appendTail(buf);
			answer(sendBody, statusCode, statusText, responseHeaders, buf.toString());
		}else{
			DataInputStream responseBody = new DataInputStream(new FileInputStream(file));
			answer(sendBody, statusCode, statusText, responseHeaders, responseBody, file.length());
			responseBody.close();
		}
	}

	private HashMap<String, String> parseQuery(String query) throws Exception{
		HashMap<String, String> ans = new HashMap<>();
		String[] fields = query.split("&");
		for(String field : fields){
			String[] parts = field.split("=");
			String key = parts[0];
			String value = "";
			if(parts.length > 1) value = parts[1];
			ans.put(URLDecoder.decode(key, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
		}
		return ans;
	}

	private void authorizate() throws Exception{
		HashMap<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("WWW-Authenticate", "Basic realm=\"No tienes permiso de ver esta carpeta, a menos que inicies sesion:\", charset=\"UTF-8\"");
		answer(true, 401, "Unauthorized", responseHeaders, new File("Forbidden.html"), null, null);
	}

	@Override
	public void run(){
		try{
			String request = input.readLine();
			if(request == null) return;
			Matcher m = requestPattern.matcher(request);
			if(!m.find()){
				System.out.println(" Peticion invalida: " + request);
				return;
			}
			String method = m.group(1);
			String url = m.group(3);
			System.out.println(" Se obtuvo peticion desde " + addr + ":" + port + " de tipo " + method + " con url " + url);

			String[] partsOfUrl = url.split("\\?");
			String requestPath = partsOfUrl[0];
			if(requestPath.length() > 0 && requestPath.charAt(0) == '/')
				requestPath = requestPath.substring(1);
			if(requestPath.length() == 0)
				requestPath = "index.html";
			requestPath = URLDecoder.decode(requestPath, "UTF-8");

			String header;
			HashMap<String, String> requestHeaders = new HashMap<>();
			while((header = input.readLine()).length() > 0){
				m = headerPattern.matcher(header);
				if(!m.find()) continue;
				String key = m.group(1);
				String value = m.group(2);
				requestHeaders.put(key.toLowerCase(), value);
			}

			long contentLength = 0;
			String contentType = "";
			if(requestHeaders.containsKey("content-length")){
				contentLength = Long.parseLong(requestHeaders.get("content-length"));
			}
			if(requestHeaders.containsKey("content-type")){
				contentType = requestHeaders.get("content-type");
			}

			String queryString = partsOfUrl.length > 1 ? partsOfUrl[1] : "";
			String postRequest = "";
			HashMap<String, String> getMap = parseQuery(queryString);
			
			if(contentType.equalsIgnoreCase("application/x-www-form-urlencoded")){
				byte[] buf = new byte[(int)contentLength];
				input.read(buf);
				postRequest = new String(buf);
			}
			HashMap<String, String> postMap = parseQuery(postRequest);

			if(requestHeaders.containsKey("authorization")){
				byte[] data = Base64.getDecoder().decode(requestHeaders.get("authorization").substring(6));
				String[] userPass = new String(data).split(":");
				if(userPass.length > 0 && userPass[0].equals(user) && userPass[1].equals(pass)){
					logged.put(addr + ":" + port, true);
				}
			}

			File f = new File(requestPath);

			boolean sendBody = !method.equals("HEAD");

			switch(method){
				case "HEAD":
				case "GET":
				case "POST":{
					if(!f.exists()){
						answer(sendBody, 404, "Not Found", null, new File("NotFound.html"), null, null);
					}else{
						if(!f.isDirectory()){
							answer(sendBody, 200, "OK", null, f, getMap, postMap);
						}else{
							if(logged.containsKey(addr + ":" + port) && logged.get(addr + ":" + port)){
								String msg = "<!DOCTYPE html>\n<meta charset=\"utf-8\">\n<html>\n<head>\n <title> Index of /" + requestPath + "</title>\n</head>\n<body>\n <h1>Index of /" + requestPath + "</h1>\n";
								msg += " <table>\n  <tr><th>Name</th><th>Last modified</th><th>Size</th></tr>\n";
								msg += "  <tr><th colspan=\"3\"><hr /></th></tr>\n";
								for(File item : f.listFiles()){
									msg += "  <tr>\n";
									long len = item.length();
									String size = "";
									if(item.isDirectory()) size = "-";
									else if(len < 1024) size = String.valueOf(len) + " B";
									else if(len < 1048576) size = String.format("%.2f KB", len / 1024.0);
									else if(len < 1073741824) size = String.format("%.2f MB", len / 1048576.0);
									else size = String.format("%.2f GB", len / 1073741824.0);
									msg += "  <td><a href = \"/" + (new File(f, item.getName())).getPath() + "\">" + item.getName() + "</a></td><td>" + sdf.format(item.lastModified()) + "</td><td align=\"right\">" + size + "</td>\n";
									msg += "  </tr>\n";
								}
								msg += " </table>\n</body>\n</html>";
								answer(sendBody, 200, "OK", null, msg);
							}else{
								authorizate();
							}
						}
					}
					break;
				}
				case "PUT":{
					if(logged.containsKey(addr + ":" + port) && logged.get(addr + ":" + port)){
						byte[] buffer = new byte[bufferSize];
						int read = 0;
						long received = 0;
						FileOutputStream fos = new FileOutputStream(f);
						DataOutputStream dos = new DataOutputStream(fos);
						while(received < contentLength){
							read = input.read(buffer, 0, (int)Math.min(bufferSize, contentLength - received));
							received += read;
							dos.write(buffer, 0, read);
						}
						dos.close();
						fos.close();
						HashMap<String, String> responseHeaders = new HashMap<>();
						responseHeaders.put("Content-Location", "/" + requestPath);
						answer(true, 201, "Created", responseHeaders, f, null, null);
					}else{
						authorizate();
					}
					break;
				}
				case "DELETE":{
					if(!f.exists()){
						answer(true, 404, "Not Found", null, new File("NotFound.html"), null, null);
					}else{
						if(f.isDirectory()){
							answer(true, 403, "Forbidden", null, new File("Forbidden.html"), null, null);
						}else{
							if(logged.containsKey(addr + ":" + port) && logged.get(addr + ":" + port)){
								f.delete();
								answer(true, 200, "OK", null, new File("borrado.html"), null, null);
							}else{
								authorizate();
							}
						}
					}
					break;
				}
			}
			input.close();
			output.close();
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}