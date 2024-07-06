# SuperCollider Array Tutorial

Arrays in SuperCollider are powerful and flexible collections that are fundamental to many operations in the language. This tutorial will cover the basics of creating, manipulating, and using arrays in SuperCollider.

## 1. Creating Arrays

There are several ways to create arrays in SuperCollider:

```supercollider
// Method 1: Using square brackets
a = [1, 2, 3, 4, 5];
a.postln; // Posts: [ 1, 2, 3, 4, 5 ]

// Method 2: Using Array.new(size)
b = Array.new(3);
b.add(1); b.add(2); b.add(3);
b.postln; // Posts: [ 1, 2, 3 ]

// Method 3: Using Array.fill
c = Array.fill(5, { arg i; i * 10 });
c.postln; // Posts: [ 0, 10, 20, 30, 40 ]

// Method 4: Using Array.series
d = Array.series(7, 100, 10);
d.postln; // Posts: [ 100, 110, 120, 130, 140, 150, 160 ]
```

## 2. Accessing Array Elements

Elements in an array are indexed starting from 0:

```supercollider
a = ['do', 're', 'mi', 'fa', 'sol'];
a[0].postln; // Posts: do
a[2].postln; // Posts: mi
a[4].postln; // Posts: sol

// Negative indices count from the end
a[-1].postln; // Posts: sol (last element)
a[-2].postln; // Posts: fa (second to last element)
```

## 3. Modifying Arrays

Arrays can be modified in various ways:

```supercollider
a = [1, 2, 3, 4, 5];

// Change a single element
a[2] = 10;
a.postln; // Posts: [ 1, 2, 10, 4, 5 ]

// Add an element to the end
a = a.add(6);
a.postln; // Posts: [ 1, 2, 10, 4, 5, 6 ]

// Insert an element at a specific index
a = a.insert(3, 100);
a.postln; // Posts: [ 1, 2, 10, 100, 4, 5, 6 ]

// Remove an element
a = a.removeAt(2);
a.postln; // Posts: [ 1, 2, 100, 4, 5, 6 ]
```

## 4. Array Operations

SuperCollider provides many operations for working with arrays:

```supercollider
a = [1, 2, 3, 4, 5];
b = [10, 20, 30, 40, 50];

// Concatenation
c = a ++ b;
c.postln; // Posts: [ 1, 2, 3, 4, 5, 10, 20, 30, 40, 50 ]

// Reversing
d = a.reverse;
d.postln; // Posts: [ 5, 4, 3, 2, 1 ]

// Shuffling
e = a.scramble;
e.postln; // Posts a random permutation of a

// Sorting
f = [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5].sort;
f.postln; // Posts: [ 1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9 ]
```

## 5. Array Methods

Arrays have many useful methods:

```supercollider
a = [1, 2, 3, 4, 5];

// Sum of all elements
a.sum.postln; // Posts: 15

// Product of all elements
a.product.postln; // Posts: 120

// Mean (average) of elements
a.mean.postln; // Posts: 3

// Finding the minimum and maximum
a.minItem.postln; // Posts: 1
a.maxItem.postln; // Posts: 5

// Checking if an element exists
a.includes(3).postln; // Posts: true
a.includes(6).postln; // Posts: false
```

## 6. Iterating Over Arrays

SuperCollider provides several ways to iterate over arrays:

```supercollider
a = [1, 2, 3, 4, 5];

// Using do
a.do({ arg item, i; 
    ("Index: " ++ i ++ ", Value: " ++ item).postln; 
});

// Using collect (returns a new array)
b = a.collect({ arg item; item * 2 });
b.postln; // Posts: [ 2, 4, 6, 8, 10 ]

// Using select (filters array based on a condition)
c = a.select({ arg item; item.even });
c.postln; // Posts: [ 2, 4 ]

// Using reject (opposite of select)
d = a.reject({ arg item; item.even });
d.postln; // Posts: [ 1, 3, 5 ]
```

## 7. Arrays in Synth Definitions

Arrays are often used in SynthDefs for various purposes:

```supercollider
(
SynthDef(\arrayExample, {
    var freqs, amps, sig;
    
    // Array of frequencies
    freqs = [300, 400, 500, 600, 700];
    
    // Array of amplitudes
    amps = [0.5, 0.4, 0.3, 0.2, 0.1];
    
    // Create a signal from these arrays
    sig = SinOsc.ar(freqs, 0, amps).sum;
    
    Out.ar(0, sig ! 2);
}).add;
)

// Play the synth
x = Synth(\arrayExample);

// Stop the synth after 2 seconds
s.waitForBoot {
    2.wait;
    x.free;
    "Array example complete.".postln;
};
```

This tutorial covers the basics of working with arrays in SuperCollider. Arrays are fundamental to many operations in SC, especially when working with multiple parameters or creating complex synthesis structures. Practice with these examples to become familiar with array operations in SuperCollider.
