Write tests for the `releaseNewEdition` method that is used to add new editions of books.

Achieve 100% branch+condition coverage of this method Mock only what needs to be mocked.

You may use verification statement of the format

```
verify(mock, times(n)).method( param 1, param 2....)
```
where the statement verifies that the method() with the specific parameters is called for the MOCKED object exactly "n" times.

AND 

```
verify(mock, never()).method( param 1, param 2....)

```
where the  statement verifies that the method() with the specific parameters is never called for the MOCKED object.