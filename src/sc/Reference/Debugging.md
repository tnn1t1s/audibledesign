# SuperCollider Debugging and Cleanup Guide

When working with SuperCollider, it's common to encounter issues with lingering synths, buffers, or other server-side resources. This guide provides steps to diagnose and clear out various states that might cause problems.

## 1. Stopping All Sound

If you need to immediately stop all sound:

```supercollider
s.freeAll;  // Free all synths
```

## 2. Clearing Server State

To reset the server to a clean state:

```supercollider
(
s.freeAll;  // Free all synths
s.freeSoundFiles; // Remove all loaded sound files
s.freeAllNotes; // Free all notes
s.freeAllBuffers;  // Free all buffers
s.newAllocators;  // Reset node ID allocator
s.initTree;  // Re-initialize node tree
s.sync;
"Server state cleared.".postln;
)
```

## 3. Stopping and Restarting the Server

If you need a completely fresh start:

```supercollider
(
s.quit;
s.waitForBoot({
    "Server restarted.".postln;
});
)
```

## 4. Clearing Client-Side Resources

To clear variables in the current environment:

```supercollider
currentEnvironment.clear;
```

Note: This will clear all variables you've defined, so use with caution.

## 5. Stopping Scheduled Tasks and Routines

To stop all scheduled tasks and routines:

```supercollider
thisProcess.clock.clear;
```

## 6. Clearing MIDI

If you're working with MIDI:

```supercollider
MIDIClient.disposeClientPortsAndDestinations;
```

## 7. Debugging Node Tree

To see what synths are currently running:

```supercollider
s.queryAllNodes;
```

This will post the entire node tree to the post window, helping you identify any lingering synths.

## 8. Checking Buffer Status

To see all currently allocated buffers:

```supercollider
s.bufferAllocator.debug;
```

## 9. Debugging Specific Synths

If you know the name of a problematic SynthDef:

```supercollider
(
~debugSynth = { |defName|
    s.queryAllNodes(action: { |reply|
        reply.do { |node|
            if(node.defName == defName) {
                "Found synth: %".format(node).postln;
            }
        }
    });
};

// Usage:
~debugSynth.value(\yourSynthDefName);
)
```

## 10. Memory Usage

To check server memory usage:

```supercollider
s.avgCPU.postln;  // Average CPU usage
s.peakCPU.postln;  // Peak CPU usage
s.numSynths.postln;  // Number of synths
s.numGroups.postln;  // Number of groups
s.numSynthDefs.postln;  // Number of SynthDefs
```

## 11. Comprehensive Cleanup Function

Here's a comprehensive cleanup function you can use:

```supercollider
(
~cleanupEverything = {
    s.freeAll;
    s.freeSoundFiles;
    s.freeAllNotes;
    s.freeAllBuffers;
    s.newAllocators;
    s.initTree;
    thisProcess.clock.clear;
    currentEnvironment.clear;
    MIDIClient.disposeClientPortsAndDestinations;
    s.sync;
    "Comprehensive cleanup complete.".postln;
    s.queryAllNodes;  // Show the clean node tree
};

// Usage:
~cleanupEverything.value;
)
```

## 12. Dealing with Zombie Processes

Sometimes, the SuperCollider server process might not quit properly. In such cases:

1. Quit SuperCollider completely.
2. Open your system's task manager or activity monitor.
3. Look for any lingering `scsynth` or `supernova` processes.
4. Force quit these processes if found.
5. Restart SuperCollider.

## Best Practices to Avoid Issues

1. Always free synths when you're done with them: `mySynth.free;`
2. Use `CmdPeriod.add` to register cleanup functions that run when you hit Cmd+.
3. Group related synths and free the group instead of individual synths.
4. Use `Bus.clear` and `Buffer.free` to clean up buses and buffers.
5. Wrap long-running processes in a routine or task for easier management.

Remember, prevention is better than cure. Regularly cleaning up resources as you work can prevent many debugging headaches later on.
