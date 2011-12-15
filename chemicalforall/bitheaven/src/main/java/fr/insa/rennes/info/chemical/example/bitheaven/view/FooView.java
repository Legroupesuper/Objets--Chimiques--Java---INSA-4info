package fr.insa.rennes.info.chemical.example.bitheaven.view;

import java.util.Collection;

import org.GNOME.Accessibility.CollectionHolder;
import org.omg.CORBA.Any;
import org.omg.CORBA.TypeCode;
import org.omg.IOP.Codec;
import org.omg.IOP.CodecFactoryOperations;
import org.omg.IOP.CodecOperations;
import org.omg.IOP.Encoding;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
import org.omg.IOP.CodecPackage.TypeMismatch;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

import com.sun.corba.se.impl.oa.poa.Policies;
import com.sun.corba.se.pept.transport.Acceptor;
import com.sun.corba.se.pept.transport.ByteBufferPool;
import com.sun.corba.se.pept.transport.ContactInfo;
import com.sun.corba.se.pept.transport.InboundConnectionCache;
import com.sun.corba.se.pept.transport.OutboundConnectionCache;
import com.sun.corba.se.pept.transport.Selector;
import com.sun.corba.se.spi.ior.IORTemplate;
import com.sun.corba.se.spi.ior.ObjectAdapterId;
import com.sun.corba.se.spi.transport.CorbaTransportManager;
import com.sun.org.apache.xml.internal.dtm.ref.CoroutineManager;
import com.sun.org.apache.xml.internal.dtm.ref.CoroutineParser;

final class FooView implements CodecOperations,
		CodecFactoryOperations, CoroutineParser,
		CorbaTransportManager {

	public FooView() {
		// TODO Auto-generated constructor stub
	}

	public Any decode(byte[] arg0) throws FormatMismatch {
		// TODO Auto-generated method stub
		return null;
	}

	public Any decode_value(byte[] arg0, TypeCode arg1) throws FormatMismatch,
			TypeMismatch {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] encode(Any arg0) throws InvalidTypeForEncoding {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] encode_value(Any arg0) throws InvalidTypeForEncoding {
		// TODO Auto-generated method stub
		return null;
	}

	public Codec create_codec(Encoding arg0) throws UnknownEncoding {
		// TODO Auto-generated method stub
		return null;
	}

	public Object doMore(boolean arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object doParse(InputSource arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void doTerminate(int arg0) {
		// TODO Auto-generated method stub

	}

	public CoroutineManager getCoroutineManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getParserCoroutineID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void init(CoroutineManager arg0, int arg1, XMLReader arg2) {
		// TODO Auto-generated method stub

	}

	public void setContentHandler(ContentHandler arg0) {
		// TODO Auto-generated method stub

	}

	public void setLexHandler(LexicalHandler arg0) {
		// TODO Auto-generated method stub

	}

	public void addToIORTemplate(IORTemplate arg0, Policies arg1, String arg2,
			String arg3, ObjectAdapterId arg4) {
		// TODO Auto-generated method stub

	}

	public Collection getAcceptors(String arg0, ObjectAdapterId arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public Collection getAcceptors() {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBufferPool getByteBufferPool(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public InboundConnectionCache getInboundConnectionCache(Acceptor arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getInboundConnectionCaches() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutboundConnectionCache getOutboundConnectionCache(ContactInfo arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getOutboundConnectionCaches() {
		// TODO Auto-generated method stub
		return null;
	}

	public Selector getSelector(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerAcceptor(Acceptor arg0) {
		// TODO Auto-generated method stub

	}

	public void unregisterAcceptor(Acceptor arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
