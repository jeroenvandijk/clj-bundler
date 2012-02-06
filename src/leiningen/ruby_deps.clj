(ns leiningen.ruby-deps
  (:import [org.jruby CompatVersion]
           [org.jruby.embed ScriptingContainer LocalContextScope]))
; 
(defn set-container-version
 "Sets the version of a container to RUBY1_9"
 [container]
 (. container setCompatVersion (. CompatVersion RUBY1_9)))

(defonce container
 (let [container (ScriptingContainer. LocalContextScope/SINGLETHREAD)]
   (do (set-container-version container)
       container)))

(defn execute
  "Takes a string of Ruby code and runs it.

  Usage: (execute \"'hello'.reverse\") ;=> \"olleh\""
  [rb-string]
  (. container runScriptlet rb-string))

(defn ruby-deps 
  "Installs your ruby dependencies"
  [& args]
  (execute (str "
    $:.unshift File.join(File.dirname(__FILE__), 'leiningen/bundler-1.0.21/lib')
    require 'bundler'
    require 'bundler/cli'
    cli = Bundler::CLI.new
    cli.options = { :path => 'lib' }
    cli.install
  ")))
