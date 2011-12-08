package com.chatlogs.core.util

import java.io.{StringWriter, File, FileInputStream, InputStream}

/**
 * This utility of IO process.<br>
 * User: fqtrnt [2011/12/01]
 * Version: 1.0.0
 */

object IOUtils {
  implicit def filePathAsInputStream(filePath: String): InputStream = {
    if (filePath startsWith "classpath://") {
      getClass.getClassLoader.getResourceAsStream(filePath.replaceFirst("classpath://", ""))
    } else {
      new FileInputStream(filePath)
    }
  }

  implicit def fileExcavation(path: String): FileExcavation = new FileExcavation(path)
}

class FileExcavation(target: String) {
  def excavate: Array[String] = {
    val file = new File(target)
    if (!file.exists) return Array()
    subListFiles(file)
  }

  private def subListFiles(folder: File): Array[String] = if (folder.isFile) Array(folder.getAbsolutePath) else folder.listFiles flatMap subListFiles
}