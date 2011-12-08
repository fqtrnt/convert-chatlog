package com.chatlogs.core.util

import java.io.{FileInputStream, InputStream}

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
}