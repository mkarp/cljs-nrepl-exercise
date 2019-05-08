# ClojureScript nREPL Exercise

This is a test repo to verify that I'm still sane while setting up ClojureScript REPL via nREPL.

### Usage:

To start nREPL via `lein repl`:

```
lein nrepl
```

To start the REPL with rebel-readline:

```
lein rebl
```

This would start an nREPL server automatically. Then call `(fig)` to start figwheel-main with rebel-readline.

To start the REPL using `clj`:

```
clj -A:rebl
```

Same as above, this would start an nREPL server automatically. Then call `(fig)` to start figwheel-main with rebel-readline.
