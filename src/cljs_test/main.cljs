(ns cljs-test.main)

(defn hello-world [msg]
  (str "Hello world " msg))

(defn log [msg]
  (js/console.log (hello-world msg)))

(comment
  (hello-world "Foo")
  (hello-world "Bar")
  (log "Foo"))
