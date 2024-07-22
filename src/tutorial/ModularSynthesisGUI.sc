[Previous content remains the same]

## 10. Adding a GUI

To make our virtual modular synthesizer more user-friendly, let's create a GUI that allows us to control various parameters visually. We'll use SuperCollider's built-in GUI classes to create sliders, knobs, and buttons for our synthesizer.

First, make sure you have the QtGUI library loaded:

```supercollider
Server.default.boot;
```

Now, let's create our GUI:

```supercollider
(
Window.closeAll; // Close any open windows

w = Window("Modular Synth", Rect(100, 100, 600, 400)).front;
w.view.decorator = FlowLayout(w.bounds);

// Oscillator controls
StaticText(w, 150@20).string_("Oscillators:");
3.do { |i|
    var oscType = ['Square', 'Sine', 'Saw'][i];
    EZKnob(w, 150@100, oscType + " Freq", \freq,
        {|ez| [~osc1, ~osc2, ~osc3][i].set(\freq, ez.value)},
        110, layout: \vert2)
};

w.view.decorator.nextLine;

// LFO controls
StaticText(w, 150@20).string_("LFOs:");
2.do { |i|
    var lfoType = ['Sine LFO', 'Square LFO'][i];
    EZKnob(w, 150@100, lfoType + " Rate", \freq,
        {|ez| [~lfo1, ~lfo2][i].set(\freq, ez.value)},
        0.1, layout: \vert2)
};

// Filter controls
StaticText(w, 150@20).string_("Filter:");
EZKnob(w, 150@100, "Cutoff", \freq,
    {|ez| ~filter.set(\cutoff, ez.value)},
    1000, \exp, layout: \vert2);
EZKnob(w, 150@100, "Resonance", \rq,
    {|ez| ~filter.set(\res, ez.value)},
    0.5, layout: \vert2);

w.view.decorator.nextLine;

// Envelope controls
StaticText(w, 150@20).string_("Envelope:");
['A', 'D', 'S', 'R'].do { |param|
    EZKnob(w, 100@100, param, [0.01, 4, \exp],
        {|ez| ~env.set(param.toLower, ez.value)},
        [0.01, 0.3, 0.5, 1][['A', 'D', 'S', 'R'].indexOf(param)],
        layout: \vert2)
};

// Gate button
Button(w, 150@30)
    .states_([["Gate On", Color.black, Color.green], ["Gate Off", Color.white, Color.red]])
    .action_({|btn| ~env.set(\gate, btn.value)});

w.view.decorator.nextLine;

// Master volume
EZSlider(w, 580@20, "Master Volume", \db,
    {|ez| s.volume.volume = ez.value.dbamp.ampdb}, -20,
    labelWidth: 120, numberWidth: 60);

// Scope
s.scope;
)
```

This GUI provides controls for:

1. Three oscillators (frequency control for each)
2. Two LFOs (rate control for each)
3. Filter (cutoff and resonance controls)
4. Envelope (ADSR controls)
5. Gate button for triggering the envelope
6. Master volume control

Additionally, we've included a scope to visualize the output.

## 11. Using the GUI

Now that we have our GUI set up, let's explore how to use it effectively:

1. **Oscillators**: Use the knobs labeled "Square Freq", "Sine Freq", and "Saw Freq" to adjust the frequencies of the three oscillators.

2. **LFOs**: The "Sine LFO Rate" and "Square LFO Rate" knobs control the speed of the LFOs. Remember, LFO1 is modulating the filter cutoff.

3. **Filter**: Adjust the "Cutoff" and "Resonance" knobs to shape the tone of the filtered oscillator (Osc1).

4. **Envelope**: The ADSR knobs control the envelope shape. Use the "Gate" button to trigger the envelope.

5. **Master Volume**: Use this slider to control the overall volume of the synth.

Try this sequence to hear the synth in action:

1. Set the oscillator frequencies to different values (e.g., 110, 220, 330 Hz).
2. Adjust the LFO rates to low values (e.g., 0.5 and 0.25 Hz).
3. Set the filter cutoff to about 1000 Hz and resonance to 0.5.
4. Adjust the envelope settings (e.g., A: 0.01, D: 0.3, S: 0.5, R: 1).
5. Press the "Gate On" button to hear the sound, and "Gate Off" to stop it.
6. While the sound is playing, try adjusting different parameters to hear how they affect the sound.

## 12. Further GUI Enhancements

To make our GUI even more powerful, consider adding these features:

1. **Patch bay**: Create a matrix of buttons to connect different modules.
2. **Presets**: Add buttons to save and recall synth settings.
3. **Modulation depth controls**: Add sliders to control how much the LFOs affect their targets.
4. **Waveform displays**: Show the waveforms of the oscillators and LFOs.

Remember, the GUI we've created is just a starting point. Feel free to modify and expand it to suit your needs and to reflect the specific modules and routing in your virtual modular synth.
