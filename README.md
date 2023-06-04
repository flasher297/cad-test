# Readme

## Arch-logic
To unify logging I decided to treat writing to file, writing to logcat, and showing toast as equal actions. So I created ILogger interface which each type of logger implements.
There's a factory that creates any type of logger. Logcat and File loggers are called directly and UI logger is decorated with a file logger and shows toast only after write-read actions to the file.

All loggers stored in a list and debounce entity make a call to each logger at least once per 2 seconds.

Clear of the log file is hidden by IClearable interface and happens inside FileRepository.

Debouncer implemented with HandlerThread backed with a worker thread.
It has postDebounceAction() method to post debounce action.
Also, it has postImmediateAction() method. It's used to immediately execute work on the background thread. It's especially useful when we try to write/read/delete files in FileRepository. Using postImmediateAction() we remove files on the same thread as we write to it.


## Counting algorithm
To implement robust adding of types of animals and seeking for suitable ranges I used HashMap where the key is CharSequence with the type of animal, and the value is ordered TreeSet.
Thus we can access the treeSet by name with constant time and add new values and read ranges with log(n) time complexity because this set is backed up with auto-balancing Red Black tree.
There's another option to use PriorityQue instead of TreeSet to have always sorted PriorityHeap, but in this case, we need to manually iterate to get a suitable range of values.

## Testing
There are two files with test.
CsAnalyticsTest uses Mockito and tests that the debouncer method is called and also tests the counting of added animals.
CsAnalyticsCountTest used Robolectric to mock posting runnables to Handler. This allows to test the correctness of log messages.