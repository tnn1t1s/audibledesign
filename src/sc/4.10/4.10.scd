(
// Load the audio files
~vocalBuf = Buffer.read(s, "/path/to/vocal_aah.wav");
~whistleBuf = Buffer.read(s, "/path/to/train_whistle.wav");
~crashBuf = Buffer.read(s, "/path/to/car_crash.wav");

// Wait for buffers to load
s.sync;

// Function to find zero crossing
~findZeroCrossing = { |buffer, startFrame = 0|
    var array = buffer.getToFloatArray(startFrame, 1000);
    var index = array.indexOf({ |sample, i|
        (sample * array[i+1]) <= 0 and: { sample.abs < 0.01 }
    });
    startFrame + index
};

// Stitch buffers at zero crossings
~stitchBuffers = { |buffers|
    var totalFrames = buffers.sum(_.numFrames);
    var stitchedBuf = Buffer.alloc(s, totalFrames);
    var writePos = 0;

    buffers.do { |buf|
        var readPos = ~findZeroCrossing.(buf);
        var numFrames = buf.numFrames - readPos;
        buf.copyData(stitchedBuf, srcStartAt: readPos, numSamples: numFrames, dstStartAt: writePos);
        writePos = writePos + numFrames;
    };

    stitchedBuf
};

// Stitch the buffers
~stitchedBuf = ~stitchBuffers.([~vocalBuf, ~whistleBuf, ~crashBuf]);

// Define the SynthDef
SynthDef(\stitchedSound, {
    arg out=0, buf, gate=1, cutoff=20000;
    var sig, env, filterEnv;
    
    // ADSR envelope
    env = EnvGen.kr(Env.adsr(0.01, 0.3, 0.5, 2), gate, doneAction: 2);
    
    // Filter envelope (same shape as ADSR)
    filterEnv = EnvGen.kr(Env.adsr(0.01, 0.3, 0.5, 2), gate);
    
    // Play the buffer
    sig = PlayBuf.ar(1, buf, doneAction: 2);
    
    // Apply low-pass filter with envelope
    sig = LPF.ar(sig, cutoff * filterEnv);
    
    // Apply amplitude envelope
    sig = sig * env;
    
    Out.ar(out, sig!2);
}).add;

// Play the stitched sound
~stitchedSound = Synth(\stitchedSound, [\buf, ~stitchedBuf]);

// Release the synth after the buffer duration
SystemClock.sched(~stitchedBuf.duration, {
    ~stitchedSound.set(\gate, 0);
});
)
