GUAVA = 'com.google.guava:guava:jar:r09'
OPEN_CSV = 'net.sf.opencsv:opencsv:jar:2.1'

define 'csv-parser' do
  project.version = '1.0'
  project.group = 'com.thoughtworks'
  compile.options.target = '1.5'

  ipr.template = 'project.ipr.template'

  compile.with OPEN_CSV
  package :jar
end