// Load a buffer with an audio file
(
~buf = Buffer.read(s, "/Users/davidpalaitis/Desktop/audible_design/scratch/audibledesign/src/sc/sounds/RickieLeeJones.wav"); // Replace with your audio file name
)

// Define the SynthDef for portamento effect
(
SynthDef(\portamento, {
    arg out=0, bufnum=0, stretchFactor=1.01, direction=1, mix=0.5;
    var original, stretched, output;
    
    // Original sound
    original = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), loop: 0);
    
    // Time-stretched version
    stretched = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum) * stretchFactor, loop: 0);
    
    // Align start or end based on direction
    stretched = if(direction > 0,
        stretched, // Start-aligned for downward portamento
        DelayN.ar(stretched, 1, 1 - (BufDur.kr(bufnum) / stretchFactor)) // End-aligned for upward portamento
    );
    
    // Mix original and stretched signals
    output = XFade2.ar(original, stretched, mix * 2 - 1);
    
    // Apply fade-out envelope
    output = output * EnvGen.kr(Env.linen(0.01, BufDur.kr(bufnum) - 0.02, 0.01), doneAction: 2);
    
    Out.ar(out, output!2);
}).add;
)

// Function to play the synth with GUI control
(
~playPortamento = {
    var synth;
    
    Window.new("Portamento Control", Rect(200, 200, 400, 200)).front.layout_(
        VLayout(
            HLayout(
                Button()
                    .states_([["Play Downward", Color.black, Color.green], ["Stop", Color.white, Color.red]])
                    .action_({|btn|
                        if(btn.value == 1) {
                            synth = Synth(\portamento, [\bufnum, ~buf, \direction, 1]);
                        } {
                            synth.free;
                        };
                    }),
                Button()
                    .states_([["Play Upward", Color.black, Color.green], ["Stop", Color.white, Color.red]])
                    .action_({|btn|
                        if(btn.value == 1) {
                            synth = Synth(\portamento, [\bufnum, ~buf, \direction, -1]);
                        } {
                            synth.free;
                        };
                    })
            ),
            VLayout(
                StaticText().string_("Stretch Factor"),
                Slider()
                    .value_(0.5)
                    .action_({|sl| 
                        var factor = sl.value.linexp(0, 1, 1.001, 1.1);
                        synth.set(\stretchFactor, factor);
                    })
            ),
            VLayout(
                StaticText().string_("Mix"),
                Slider()
                    .value_(0.5)
                    .action_({|sl| synth.set(\mix, sl.value) })
            )
        )
    );
};
)

// Run this to start
~playPortamento.value;

// When done, free the buffer
~buf.free;
