## Interface Comparable: implementation, compareTo.

### 1. The Problem of Comparing Objects

#### Reminder: Comparing References and Comparing Objects

You already know that in Java, the == operator compares objects by reference—that is, whether they are located at the same address in memory. Two objects with the same fields, but created with new, will be different for ==.

```java
Person p1 = new Person("Sasha", 20);
Person p2 = new Person("Sasha", 20);

System.out.println(p1 == p2); // false — these are different objects in memory!
```

And if we want to know if they are equal in content, we use equals(), hashCode()... well, you already know that. But what if we need to figure out which objects are "older," "younger," or "higher in alphabetical order"? For example, to sort a list of users by age or name.

#### The Need to Sorting and Search Objects

Let's say we have a list of users, and we want to sort them by age:

```java
List<Person> people = new ArrayList<>();
people.add(new Person("Vasya", 25));
people.add(new Person("Petya", 20));
people.add(new Person("Katya", 30));

// How to sort?
Collections.sort(people); // Oops! Java doesn't know how to compare Persons!
```

The compiler will immediately complain: the Person class doesn't implement the Comparable interface. Java can't read minds and doesn't know what "greater" or "less" means for a Person. To teach it this, we must explicitly describe the comparison rules.

### 2. The Comparable Interface

#### Interface Declaration

The Comparable interface is Java's standard way of telling people, "My class can be compared, and here's how."

```java
public interface Comparable<T> {
    int compareTo(T o);
}
```

The old friend a.compareTo(b) returns:

- **negative number** — a is "less than" b.
- If 0, the objects are considered equal.
- **positive number** — a is "greater than" b.

#### Example: Implementing compareTo for the Person class

Let's create a Person class that can be compared by age:

```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getters (for examples below)
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    
    // Implementation of the compareTo method
    @Override
    public int compareTo(Person other) {
        // Sort by age (ascending)
        return Integer.compare(this.age, other.age);
        // Alternative: return this.age - other.age;
    }
}
```

**Important point:** if you want to sort in descending order, simply swap the arguments: Integer.compare(other.age, this.age).

**Analogy.** compareTo is like a judge at a competition: they must clearly decide who is ahead, who is behind, and who is on the same level. If all the judges (compareTo methods) judge differently, chaos will ensue!

- **Comparable vs. Comparator — When to Use Which**

**🟢 Comparable — the “natural ordering” of an object**

Used when:

👉 an object has **one primary sorting method**

---

A person is usually sorted:

- by age
- by id
- by name

Let's say we've chosen age.

---

Implementation

```java
class Person implements Comparable<Person> {
    
    int age;
    
    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }
}
```

---

**Now you can:**

```java
List<Person> list = new ArrayList<>();
list.add(new Person("Lena", 22));
list.add(new Person("Alex", 20));
list.add(new Person("Yan", 21));
Collections.sort(list);
```

---

**🧠 Comparable =**

> “an object knows how to compare itself”

---

**🔷 Comparator — external sorting logic**

Used when:

- many sorting methods
- don't want to change the class
- time sorting is needed

---

Today we sort:

- by name

Tomorrow:

- by age

Then:

- by salary

---

**Implementation**

```java
//Main
Comparator<Person> byAge = (p1, p2) ->
Integer.compare(p1.getAge(), p2.getAge());

List<Person> people = new ArrayList<>();

people.add(new Person("Anna", 25));
people.add(new Person("Boris", 19));
people.add(new Person("Ivan", 30));
people.sort(byAge);

System.out.println(people);

//Or by name
people.sort((a, b) -> a.getName().compareTo(b.getName()));
```

**🧠 Comparator =**

> “the other object decides how to compare”

### 3. Using Comparable

#### Sorting Collections with Comparable

Now that our class implements Comparable, sorting works out of the box:

```java
List<Person> people = new ArrayList<>();
people.add(new Person("Vasya", 25));
people.add(new Person("Petya", 20));
people.add(new Person("Katya", 30));

Collections.sort(people); // Uses compareTo!

for (Person p : people) {
    System.out.println(p.getName() + " (" + p.getAge() + ")");
}
// Petya (20)
// Vasya (25)
// Katya (30)
```

The sort method on a list works similarly:

```java
people.sort(null); // If null is passed, compareTo is used
```

#### Sorting by name

If we want to sort by name, we change the implementation:

```java
@Override
public int compareTo(Person other) {
    return this.name.compareTo(other.name);
}
```

#### Sorting by multiple fields

Sometimes you need to compare by one field first, and then by another if they are equal:

```java
@Override
public int compareTo(Person other) {
    int cmp = Integer.compare(this.age, other.age);
    if (cmp != 0) return cmp;
    return this.name.compareTo(other.name);
}
```

### 4. Best Practices for Implementing Comparable

#### Adhere to the Comparable contract

- If a.compareTo(b) == 0, then b.compareTo(a) must be 0.
- If a.compareTo(b) < 0, then b.compareTo(a) must be > 0 (and vice versa).
- If a.compareTo(b) == 0, it is preferable that a.equals(b) be **true** (but this is not strictly required).

**Why is this important?**

Collections (e.g., TreeSet, TreeMap) and sorting methods may behave unpredictably if the contract is violated. For example, duplicates may appear in a collection where they should not be.

#### Don't forget about equals and hashCode

If you implement compareTo, consider whether equals and hashCode are implemented correctly. This is especially true if your class will be used in collections like HashSet or Map.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person other = (Person) o;
    return age == other.age && Objects.equals(name, other.name);
}

@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

#### Don't use nullable fields in compareTo without checking!

If a field can be null, use safe comparisons:

```java
@Override
public int compareTo(Person other) {
    return Objects.compare(this.name, other.name, Comparator.nullsFirst(String::compareTo));
}
```

#### Don't modify fields involved in compareTo if the object is already in a sorted collection.

This can result in the object being "lost" within the collection—for example, in a TreeSet or TreeMap.

### 5. Developing the Sample Application: Sorting Users

#### Step 1: Defining the Class

```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    // ... constructor, getters, compareTo, equals, hashCode ...
}
```

#### Step 2: Adding Users

```java
List<Person> people = new ArrayList<>();
people.add(new Person("Masha", 23));
people.add(new Person("Grisha", 19));
people.add(new Person("Anya", 25));
```

#### Step 3: Sort and Print

```java
Collections.sort(people);

for (Person p : people) {
    System.out.println(p.getName() + " (" + p.getAge() + ")");
}
```

**Result:**

```
Grisha (19)
Masha (23)
Anya (25)
```

### 6. How Comparable Works

```
┌──────────────────────────────┐
│ Your class (Person)          │
├──────────────────────────────┤
│ implements Comparable        │
│           ↓                  │
│ public int compareTo(T o)    │
│           ↓                  │
│ (this < o) → -1              │
│ (this == o) → 0              │
│ (this > o) → 1               │
└──────────────────────────────┘
            │
            ▼
    Collections.sort(list)
            │
            ▼
    Sorting works!
```

### 7. Useful nuances

#### How Collections.sort works

- If the list contains objects that implement Comparable, sorting will use their compareTo method.
- If they don't, a compile-time error will occur.
- For standard types (Integer, String, etc.), Comparable is already implemented.

#### Can I have multiple comparison methods?

- In a single class, there is only one "natural ordering" via Comparable.
- For alternative orderings, use Comparator (next lecture).

#### Example: compareTo for strings

```java
String a = "apple";
String b = "banana";
System.out.println(a.compareTo(b)); // negative number because "apple" < "banana"
```

#### Table: What compareTo returns

| **Comparison** | **Return Value** |
| --- | --- |
| `this < o` | `< 0` |
| `this == o` | `0` |
| `this > o` | `> 0` |

### 8. Common Mistakes in Comparable Implementation

**Error №1: Violating the compareTo contract.**

If a.compareTo(b) returns 0 and b.compareTo(a) does not, the collections will behave strangely. For example, TreeSet might consider the objects different and add both.

**Error №2: Using uninitialized (null) fields.**

If the field you're comparing against can be null, and you don't check, you'll get a NullPointerException.

**Error №3: Inconsistency between compareTo and equals.**

If compareTo says that objects are equal (0) and equals says they are different (false), this will lead to bugs when working with collections.

**Error №4: Changing fields involved in compareTo after adding to a sorted collection.**

This is like changing your last name in your passport when you're already in the alphabetical order. The collection can "lose" your object.

**Error №5: Returning only -1, 0, or 1.**

The compareTo method can return any negative or positive number, not necessarily exactly -1 or 1. But for simplicity, -1/0/1 is often used.
