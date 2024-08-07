# TEQL (Java version)

And attempt to re-write and re-design my previous [Python-based](https://github.com/dmjohnsson23/teql) text editing language in a somewhat lower-level language and with a better, more efficient design.

## Goals

* All selectors can be evaluated simultaneously in a single pass over a file
* Edits can be made with only a second pass
* The entire file does not need to be loaded into memory for either operation
* Write the regex engine from scratch so it can actually integrate with the rest of the system

### Cool ideas to look at later (but not right now!)

* Language-aware selectors (e.g. we could parse a Python file as we iterate over it, and match against the parse events) as plugins