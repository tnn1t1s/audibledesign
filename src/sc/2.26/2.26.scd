// Load a buffer with an audio file
(
~buf = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/RickieLeeJones.wav"); 
)

// Define the SynthDef for comb filtering
(
SynthDef(\combFilter, {
    arg out=0, bufnum=0, delayTime=0.01, mix=0.5;
    var input, delayed, delayed2, delayed_mix, output;
    
    // Read from the buffer
    input = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), loop: 1);
    
    // Create the delayed signal
    delayed = DelayC.ar(input, 0.2, delayTime);
	
    // Create the delayed signal
	delayed2= DelayC.ar(input, 0.6, delayTime*1.1);
    
	// First mix the two delayed signals evenly
	delayed_mix = XFade2.ar(delayed, delayed2, 0.5);
    // Mix the original and delayed signals
    output = XFade2.ar(input, delayed_mix, mix * 2 - 1);
    
    Out.ar(out, output!2);
}).add;
)

// Function to play the synth and allow interactive control
(
~playCombFilter = {
    ~combSynth = Synth(\combFilter, [\bufnum, ~buf]);
    
    // GUI for interactive control
    Window.new("Comb Filter Control", Rect(200, 200, 400, 100)).front.layout_(
        HLayout(
            VLayout(
                StaticText().string_("Delay Time (pitch)"),
                Slider()
                    .value_(0.5)
                    .action_({|sl| 
                        var delayTime = sl.value.linexp(0, 1, 0.0001, 0.5);
                        ~combSynth.set(\delayTime, delayTime);
                    })
            ),
            VLayout(
                StaticText().string_("Mix"),
                Slider()
                    .value_(0.5)
                    .action_({|sl| ~combSynth.set(\mix, sl.value) })
            )
        )
    );
};
)

// Run this to start
~playCombFilter.value;

// When done, stop the synth and free the buffer
~combSynth.free;
~buf.free;
