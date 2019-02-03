class Jgows < Formula
  desc "Scala Native Workshop"
  homepage "https://github.com/Tapad/scala-native-workshop"

  @@credentials_file = "#{ENV['HOME']}/.ivy2/.credentials"
  got_ivy_credentials = File.file?(@@credentials_file)
  if !got_ivy_credentials
    abort("Please provide your Nexus credentials in #{@@credentials_file}")
  end

  username=File.readlines(@@credentials_file).map { |line| if line =~ /user=(.+)/i; $1; end }.find{|x|!x.nil?}
  password=File.readlines(@@credentials_file).map { |line| if line =~ /password=(.+)/i; $1; end }.find{|x|!x.nil?}

  if username.nil? || password.nil?
    abort("No credentials found in #{@@credentials_file}")
  end

  url "https://#{username}:#{password}@nexus.tapad.com/repository/releases/com/tapad/workshop/jgows/{{ version }}/jgows-{{ version }}.zip"
  sha256 "{{ checksum }}"
  version "{{ version }}"

  depends_on "curl" => "7.56.0"
  depends_on "llvm" => :build
  depends_on "bdw-gc"
  depends_on "re2"
  depends_on "libidn"

  def install
    system "make", "VERSION={{ version }}", "BUILDPATH=#{buildpath}", "CREDENTIALS=#{@@credentials_file}"
    bin.install "app"
  end

  test do
    system "#{bin}/app", "--version"
  end

  caveats "If you're on Linux, please install re2, unwind and gc for your distribution. See https://github.com/scala-native/scala-native/blob/master/docs/user/setup.rst"
end