package mx.tecnetia.architecture.security.aplicacion.dto.link2loyalty;

import java.io.*;
import java.util.*;
import javax.activation.*;

public class BufferedDataSource implements DataSource {

	private byte[] _data;
	private java.lang.String _name;

	public BufferedDataSource(byte[] data, String name) {
		_data = data;
		_name = name;
	}
	

	public String getContentType() { return "application/octet-stream";}
	public InputStream getInputStream() throws IOException { return new ByteArrayInputStream(_data);}
	public String getName() { return _name;}

	/**
	* Returns an OutputStream from the DataSource
	* @returns OutputStream Array of bytes converted into an OutputStream
	*/
	public OutputStream getOutputStream() throws IOException {
		OutputStream out = new ByteArrayOutputStream();
		out.write(_data);
		return out;
	}
}
