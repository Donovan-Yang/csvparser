OPEN_CSV = 'net.sf.opencsv:opencsv:jar:2.1'

repositories.remote << 'http://repo1.maven.org/maven2/'

define 'csv-parser' do
  project.version = '1.0'
  project.group = 'com.thoughtworks'
  compile.options.target = '1.5'

  ipr.template = 'project.ipr.template'

  compile.with OPEN_CSV
  package :jar
end