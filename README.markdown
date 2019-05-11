# ClojureScript nREPL Exercise

This is a test repo to verify that I'm still sane while setting up ClojureScript REPL via nREPL.

![ClojureScript nREPL Exercise Demo](https://img.youtube.com/vi/r_ps849l-xk/0.jpg)](https://www.youtube.com/watch?v=r_ps849l-xk)

### Usage:

1. Checkout this repo.
1. Run `lein nrepl` or `clj -A:nrepl` in the command line.
1. REPL, an nREPL server and Figwheel will start one after another.
1. Connect to `nrepl://localhost:7888` in your nREPL client.
1. Type `(cljs-repl)` to connect to the Figwheel session.

Cool feature: the app will instantly re-render when evaluating code in your editor with an nREPL connection, no file saving required.
