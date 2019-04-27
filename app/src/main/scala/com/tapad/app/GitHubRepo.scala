package com.tapad.app

import json.{ObjectAccessor, accessor}

case class GitHubRepo(full_name: String, `private`: Boolean)

object GitHubRepo {
  // https://github.com/MediaMath/scala-json/blob/master/docs/USAGE.md#case-class-usage
  implicit val acc = ???
}