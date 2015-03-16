# Ordering of Contributions #

The order of beans in ordered contributions can be defined by the use of "before" and "after" constraints. The constraints may contain multiple definitions of bean names with wildcards and even regular expressions.

## Explicit names ##

Each constraint may contain multiple references to other beans' names like in the following example. The beans in list will be "first", "second" and "third", in that order:

```
<ctr:contribution to="some-list">
  <ctr:entry name="third" value="Three" constraints="after: second" />
  <ctr:entry name="second" value="Two" constraints="after: first, before: third" />
  <ctr:entry name="first" value="One" constraints="before: second, third" />
</ctr:contribution>
```

## Wildcards ##

The constraints may contain "`*`" and "?" as wildcards for multiple and single characters.

The beans in the following contribution list will be "first", "second" and "third", in that order:

```
<ctr:contribution to="some-list">
  <ctr:entry name="third" value="Three" constraints="after: *" />
  <ctr:entry name="second" value="Two" />
  <ctr:entry name="first" value="One" constraints="before: ?econd" />
</ctr:contribution>
```

Constraints with explicit names are considered more important, than constraints with wildcards. In the following contribution this is used to place the "second" bean between the "first" and the "third" one:

```
<ctr:contribution to="some-list">
  <ctr:entry name="third" value="Three" />
  <ctr:entry name="second" value="Two" constraints="before: *, after: first"/>
  <ctr:entry name="first" value="One" />
</ctr:contribution>
```

## Regular Expressions ##

Instead of wildcards you can use regular expressions. Regular expressions must be surrounded by '/'es. The beans in the following contribution list will be "first", "second" and "third", in that order:

```
<ctr:contribution to="some-list">
  <ctr:entry name="third" value="Three" constraints="after: /.*/" />
  <ctr:entry name="second" value="Two" />
  <ctr:entry name="first" value="One" constraints="before: /sec\w*/" />
</ctr:contribution>
```

## Undetermined Ordering ##

Without any constraints, the beans in ordered contributions are sorted alphabetically by their names. The beans in the following contribution list will be "first", "fourth", "second" and "third", in that order:

```
<ctr:contribution to="some-list">
  <ctr:entry name="fourth" value="Four" />
  <ctr:entry name="third" value="Three" />
  <ctr:entry name="second" value="Two" />
  <ctr:entry name="first" value="One" />
</ctr:contribution>
```

If the constraints of just some bean are defined, the rest of the beans will be sorted alphabetically. The beans in the following contribution list will be "first", "second", "third" and "fourth", in that order:

```
<ctr:contribution to="some-list">
  <ctr:entry name="fourth" value="Four" constraints="after: *" />
  <ctr:entry name="third" value="Three" />
  <ctr:entry name="second" value="Two" />
  <ctr:entry name="first" value="One" />
</ctr:contribution>
```

## Invalid Ordering ##

It is quite easy to define invalid constraints, like in the following example:

```
<ctr:contribution to="some-list">
  <ctr:entry name="second" value="Two" constraints="after: first" />
  <ctr:entry name="first" value="One" constraints="after: second" />
</ctr:contribution>
```

This will result in an exception during the creation of the Spring context.