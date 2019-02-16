class Tws < Formula
  desc "Scala Native Workshop for tws"
  homepage "https://github.com/Tapad/scala-native-workshop"

  url "http://localhost:8080/tws/{{ version }}/zips/tws.zip"

  sha256 "{{ checksum }}"
  version "{{ version }}"

  depends_on "curl"
  depends_on "libidn"
  depends_on "llvm" => :build
  depends_on "bdw-gc" => :build

  def install
      system "make", "VERSION={{ version }}", "BUILDPATH=#{buildpath}"
      bin.install "tws"
  end
end