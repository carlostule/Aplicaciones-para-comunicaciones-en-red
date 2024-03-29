import java.net.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;

public class mime{
	static HashMap<String, String> table = new HashMap<>();

	static{
		table.put("atom", "application/atom+xml");
		table.put("json", "application/json");
		table.put("map", "application/json");
		table.put("topojson", "application/json");
		table.put("jsonld", "application/ld+json");
		table.put("rss", "application/rss+xml");
		table.put("geojson", "application/vnd.geo+json");
		table.put("rdf", "application/xml");
		table.put("xml", "application/xml");
		table.put("js", "application/javascript");
		table.put("webmanifest", "application/manifest+json");
		table.put("webapp", "application/x-web-app-manifest+json");
		table.put("appcache", "text/cache-manifest");
		table.put("mid", "audio/midi");
		table.put("midi", "audio/midi");
		table.put("kar", "audio/midi");
		table.put("aac", "audio/mp4");
		table.put("f4a", "audio/mp4");
		table.put("f4b", "audio/mp4");
		table.put("m4a", "audio/mp4");
		table.put("mp3", "audio/mpeg");
		table.put("oga", "audio/ogg");
		table.put("ogg", "audio/ogg");
		table.put("opus", "audio/ogg");
		table.put("ra", "audio/x-realaudio");
		table.put("wav", "audio/x-wav");
		table.put("bmp", "image/bmp");
		table.put("gif", "image/gif");
		table.put("jpeg", "image/jpeg");
		table.put("jpg", "image/jpeg");
		table.put("png", "image/png");
		table.put("svg", "image/svg+xml");
		table.put("svgz", "image/svg+xml");
		table.put("tif", "image/tiff");
		table.put("tiff", "image/tiff");
		table.put("wbmp", "image/vnd.wap.wbmp");
		table.put("webp", "image/webp");
		table.put("jng", "image/x-jng");
		table.put("3gp", "video/3gpp");
		table.put("3gpp", "video/3gpp");
		table.put("f4p", "video/mp4");
		table.put("f4v", "video/mp4");
		table.put("m4v", "video/mp4");
		table.put("mp4", "video/mp4");
		table.put("mpeg", "video/mpeg");
		table.put("mpg", "video/mpeg");
		table.put("ogv", "video/ogg");
		table.put("mov", "video/quicktime");
		table.put("webm", "video/webm");
		table.put("flv", "video/x-flv");
		table.put("mng", "video/x-mng");
		table.put("asf", "video/x-ms-asf");
		table.put("asx", "video/x-ms-asf");
		table.put("wmv", "video/x-ms-wmv");
		table.put("avi", "video/x-msvideo");
		table.put("cur", "image/x-icon");
		table.put("ico", "image/x-icon");
		table.put("doc", "application/msword");
		table.put("xls", "application/vnd.ms-excel");
		table.put("ppt", "application/vnd.ms-powerpoint");
		table.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		table.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		table.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		table.put("woff", "application/font-woff");
		table.put("woff2", "application/font-woff2");
		table.put("eot", "application/vnd.ms-fontobject");
		table.put("ttc", "application/x-font-ttf");
		table.put("ttf", "application/x-font-ttf");
		table.put("otf", "font/opentype");
		table.put("ear", "application/java-archive");
		table.put("jar", "application/java-archive");
		table.put("war", "application/java-archive");
		table.put("hqx", "application/mac-binhex40");
		table.put("bin", "application/octet-stream");
		table.put("deb", "application/octet-stream");
		table.put("dll", "application/octet-stream");
		table.put("dmg", "application/octet-stream");
		table.put("exe", "application/octet-stream");
		table.put("img", "application/octet-stream");
		table.put("iso", "application/octet-stream");
		table.put("msi", "application/octet-stream");
		table.put("msm", "application/octet-stream");
		table.put("msp", "application/octet-stream");
		table.put("safariextz", "application/octet-stream");
		table.put("pdf", "application/pdf");
		table.put("ai", "application/postscript");
		table.put("eps", "application/postscript");
		table.put("ps", "application/postscript");
		table.put("rtf", "application/rtf");
		table.put("kml", "application/vnd.google-earth.kml+xml");
		table.put("kmz", "application/vnd.google-earth.kmz");
		table.put("wmlc", "application/vnd.wap.wmlc");
		table.put("7z", "application/x-7z-compressed");
		table.put("bbaw", "application/x-bb-appworld");
		table.put("torrent", "application/x-bittorrent");
		table.put("crx", "application/x-chrome-extension");
		table.put("cco", "application/x-cocoa");
		table.put("jardiff", "application/x-java-archive-diff");
		table.put("jnlp", "application/x-java-jnlp-file");
		table.put("run", "application/x-makeself");
		table.put("oex", "application/x-opera-extension");
		table.put("pl", "application/x-perl");
		table.put("pm", "application/x-perl");
		table.put("pdb", "application/x-pilot");
		table.put("prc", "application/x-pilot");
		table.put("rar", "application/x-rar-compressed");
		table.put("rpm", "application/x-redhat-package-manager");
		table.put("sea", "application/x-sea");
		table.put("swf", "application/x-shockwave-flash");
		table.put("sit", "application/x-stuffit");
		table.put("tcl", "application/x-tcl");
		table.put("tk", "application/x-tcl");
		table.put("crt", "application/x-x509-ca-cert");
		table.put("der", "application/x-x509-ca-cert");
		table.put("pem", "application/x-x509-ca-cert");
		table.put("xpi", "application/x-xpinstall");
		table.put("xhtml", "application/xhtml+xml");
		table.put("xsl", "application/xslt+xml");
		table.put("zip", "application/zip");
		table.put("css", "text/css");
		table.put("htm", "text/html");
		table.put("html", "text/html");
		table.put("shtml", "text/html");
		table.put("mml", "text/mathml");
		table.put("txt", "text/plain");
		table.put("vcard", "text/vcardv");
		table.put("vcf", "text/vcard");
		table.put("xloc", "text/vnd.rim.location.xloc");
		table.put("jad", "text/vnd.sun.j2me.app-descriptor");
		table.put("wml", "text/vnd.wap.wml");
		table.put("vtt", "text/vtt");
		table.put("htc", "text/x-component");
	}

	static String get(String ext){
		if(table.containsKey(ext)){
			return table.get(ext);
		}else{
			return "application/octet-stream";
		}
	}
}