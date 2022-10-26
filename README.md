# rectangles

Project to analyze rectangles and features that exist among rectangles.

### Notes

The project is built as a jar file and can be executed using the command line interface described below. The only
project dependency is Apache Commons CLI. The current implementation supports rectangles consisting of horizontal
and vertical lines. The primary class **Rectangle** is composed of **Point** and **LineSegment** instances.

My geometry is rusty so, in the interest of time, I opted to implement the algorithms somewhat naively. I analyzed
properties of the various relations between rectangles and implemented the algorithms based on
comparisons of sides rather than mathematical equations. If I were to do this again, I'm not sure that I would take the
same approach. The operations that can be reasonably supported are minimal and boolean logic is less standardized than
math, arguably harder to read, and correctness is more difficult to confirm. In a real work scenario, if
requirements allowed, I would likely opt to use a library such as Apache Commons Geometry rather than implement the
equations myself.

That said, I attempted to keep the public method signatures clean and generic and attempted to keep the implementation
code clear and documented.

### Build & Run

**JDK 17+** & **maven** are required to build the project.
<br><br>
executing

```bash
mvn package
```

from the root project directory will generate **rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar** in the **./target**
directory.

The jar file can be executed like so:
Intersection of r1 & r2:

```bash
java -jar rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar -r1 2 5 9 9 -r2 5 4 8 6 -i
```

Adjacency of r1 & r2:

```bash
java -jar rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar -r1 2 5 9 9 -r2 5 4 8 6 -a
```

Containment of r1 & r2:

```bash
java -jar rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar -r1 2 5 9 9 -r2 5 4 8 6 -c
```

Print points of r1 & r2:

```bash
java -jar rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar -r1 2 5 9 9 -r2 5 4 8 6
```

Intersection, containment, and adjacency operations

```bash
java -jar rectangles-1.0-SNAPSHOT-jar-with-dependencies.jar -r1 2 5 9 9 -r2 5 4 8 6 -iac 
```

The CLI options & descriptions are as follows:

```
required options: r1, r2
 -a,--adjacency                            Output true or false indicating whether
                                           rectangle 1 and 2 are adjacent.
 -c,--containment                          Output true or false indicating whether
                                           rectangle 1 contains rectangle 2.
 -i,--intersection                         Output the points of intersection of
                                           rectangle 1 and rectangle 2.
 -r1,--rectangle1 <p1x> <p1y> <p2x> <p2y>  First rectangle and the x & y points for
                                           the first corner and the x & y points for
                                           the opposite corner.
 -r2,--rectangle2 <p1x> <p1y> <p2x> <p2y>  Second rectangle and the x & y points for
                                           the first corner and the x & y points for
                                           the opposite corner.
```

### Expansions

In addition to the intersection, containment, and adjacency detection operations the following methods are also
included:

- getWidth
- getHeight
- getPoints
- getAdjacency (returns the adjacency type. could replace isAdjacentTo)

However, I have not yet included CLI options for these operations.

### TODOs

- [ ] rectangle coupled with point number type
- [ ] refactor to include parent class / interface rectangle & existing rectangle moved to a subclass / implementation (
  to allow impl's that support rotation, math based operations, etc)
- [ ] tool to draw two rectangles for visualization?
- [ ] tool to generate rectangle test data?
