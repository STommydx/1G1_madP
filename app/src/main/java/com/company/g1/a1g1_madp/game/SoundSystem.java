package com.company.g1.a1g1_madp.game;

public class SoundSystem {

	private Game context;
	private PlaySoundListener listener;

	public SoundSystem(Game context) {
		this.context = context;
		listener = null;
	}

	public void setPlaySoundListener(PlaySoundListener listener) {
		this.listener = listener;
	}

	public void firePlaySound(SoundType soundType) {
		if (listener != null) listener.onPlaySound(soundType);
	}

	public enum SoundType {
		fireBullet
	}

	public interface PlaySoundListener {
		void onPlaySound(SoundType soundType);
	}

}
