package FS;

/**
* FS/FileSystemHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FileSystem.idl
* Sábado, 11 de Novembro de 2017 16h10min08s BRT
*/

public final class FileSystemHolder implements org.omg.CORBA.portable.Streamable
{
  public FS.FileSystem value = null;

  public FileSystemHolder ()
  {
  }

  public FileSystemHolder (FS.FileSystem initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = FS.FileSystemHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    FS.FileSystemHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return FS.FileSystemHelper.type ();
  }

}
