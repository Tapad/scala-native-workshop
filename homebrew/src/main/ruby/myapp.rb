class Myapp < Formula
  desc "Scala Native Workshop"
  homepage "https://github.com/Tapad/scala-native-workshop"

  url "http://localhost:8080/{{ version }}/zips/myapp.zip"
  sha256 "{{ checksum }}"

  depends_on "curl" => "7.56.0"

end