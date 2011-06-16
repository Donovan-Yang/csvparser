GUAVA = 'com.google.guava:guava:jar:r09'

define 'csv-parser' do
  project.version = '1.0'
  project.group = 'com.thoughtworks'
  compile.options.target = '1.5'

  ipr.template = 'project.ipr.template'

  compile.with GUAVA
  package :jar
end