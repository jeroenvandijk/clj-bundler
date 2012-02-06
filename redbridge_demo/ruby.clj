(ns redbridge-demo.ruby
  (:import [org.jruby CompatVersion]
           [org.jruby.embed ScriptingContainer LocalContextScope]))

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

(defn require-gem
  "Requires a Ruby gem.

   Usage: (require-gem \"rubygems\") ;=> true"
  [gem]
  (execute (str "require '" gem "'")))

(defn require-gems
  "Requires multiple Ruby gems.

   Usage: (require-gems \"rubygems\" \"haml-3.1.3/gem/haml\") ;=> true"
  [& gems]
  (doseq [gem gems]
    (require-gem gem)))

(defn call-method
  "Calls a method on an object."
  ([obj method]
     (. container callMethod obj method Object))
  ([obj method arg]
     (. container callMethod obj method arg Object)))

;; (defn methods->kws [obj]
;;   (map #(keyword (call-method % "to_s"))
;;        (call-method obj "methods")))






