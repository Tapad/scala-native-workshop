class Jgows < Formula
  desc "Scala Native Workshop"
  homepage "https://github.com/Tapad/scala-native-workshop"

  url "https://nexus.tapad.com/repository/releases/com/tapad/workshop/jgows/{{ version }}/jgows-{{ version }}.zip"
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