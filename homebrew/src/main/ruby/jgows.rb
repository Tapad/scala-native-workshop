class Myapp < Formula
  desc "Scala Native Workshop"
  homepage "https://github.com/Tapad/scala-native-workshop"

  url "http://nexus.tapad.com/{{ version }}/zips/myapp.zip"
  sha256 "{{ checksum }}"

  depends_on "curl" => "7.56.0"

  def install
    system "make", "VERSION={{ version }}", "BUILDPATH=#{buildpath}"
    bin.install "app"
  end

  test do
    system "#{bin}/app", "--version"
  end
end