package com.tapad.app

import json.{ObjectAccessor, accessor}

case class GitHubRepo(full_name: String, `private`: Boolean)

object GitHubRepo {
  implicit val acc = ObjectAccessor.create[GitHubRepo]
}