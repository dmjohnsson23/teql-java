# TEQL (Java version)

And attempt to re-write and re-design my previous [Python-based](https://github.com/dmjohnsson23/teql) text editing language in a somewhat lower-level language and with a better, more efficient design.

## Goals

* All selectors can be evaluated simultaneously in a single pass over a file
* Edits can be made with only a second pass
* The entire file does not need to be loaded into memory for either operation
* Write the regex engine from scratch so it can actually integrate with the rest of the system
* Nested selectors; e.g. `<SELECTOR> IN <SELECTOR>`. Could be implemented in the parser by processing both the inner and outer selectors, and returning only matches from the inner that overlap with the outer. Though, some special cases (`BEFORE`, `AFTER`, `BETWEEN`) could be optimized.

### Cool ideas to look at later (but not right now!)

* Language-aware selectors (e.g. we could parse a Python file as we iterate over it, and match against the parse events) as plugins

## Selectors

* A string literal, matching the exact text: `"match me"`
* A regex literal: `/[a-z]+/`
* A direct line selection: `LINE 5`, `LINES 4-7`, `LINES 5,11,13`
* A `BEFORE` relative selector: `"match this" BEFORE "stop matching after this"`
* An `AFTER` relative selector: `"match this" AFTER "start after matching here"`
* A `BETWEEN` relative selector: `"match this" BETWEEN "start after matching here" AND "stop matching after this"`
* A generic `IN` relative selector: `"match this" IN LINES 1-4`
* An `EVERYTHING` selector (to use in conjunction with relative selectors): `EVERYTHING AFTER LINE 7`
* An inclusive range selector: `FROM "start here" TO "stop here"`
* An exclusive range selector: `BETWEEN "start here" AND "stop here"`
* `START` and `END` cursor positions
* `SEEK` cursor positions: `SEEK 35`
* Relative cursor positions: `OFFSET -5 FROM END`
* Narrow multiple matches: `FIRST "match me"`, `LAST "match me"`, `5-6 "match me"` (`BEFORE`, `AFTER`, and `BETWEEN` implicitly use `FIRST`)

## Editing Operations

* `INSERT "string" AT LINE 4`
* `INSERT "string" BEFORE LINE 4`
* `CHANGE /this/ TO "that"`
* `REPLACE /this/ WITH "that"` (Alias of `CHANGE`)
* `DELETE EVERYTHING AFTER LINE 42`
* Transitions with `BEGIN` and `COMMIT` to perform operations simultaneously rather than sequentially.