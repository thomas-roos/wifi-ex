A yocto layer setup to build a Rasperry Pi wifi config example image.


## Building with bitbake-setup

### Quick Start

1. Clone with submodules:

```bash
git clone --recurse-submodules https://github.com/thomas-roos/wifi-ex
```

Or if already cloned:
```bash
git submodule update --init --recursive
```

2. Initialize the build environment:

```bash
cd bitbake/bin/ && \
./bitbake-setup --setting default top-dir-prefix $PWD/../../ init \
  $PWD/../../bitbake-setup.conf.json \
  wifi-ex distro/poky-altcfg application/wifi-ex core/yocto/sstate-mirror-cdn --non-interactive && \
  cd -
```

3. Source the build environment:

```bash
. ./bitbake-builds/bitbake-setup-wifi-ex-distro_poky-altcfg/build/init-build-env
```

4. Build the image:

```bash
bitbake core-image-base
```
(core-image-minimal is not enough -> https://meta-raspberrypi.readthedocs.io/en/latest/layer-contents.html#wifi-and-bluetooth-firmware)

5. Resulting image:

```bash
./bitbake-builds/bitbake-setup-wifi-ex-distro_poky-altcfg/build/tmp/deploy/images/raspberrypi-armv8/core-image-base-raspberrypi-armv8.rootfs.wic.bz2
```

6. Flash the image onto your sd card

Be careful using the correct device! Can also be done with the rpi-imager.

```bash
bzcat core-image-base-raspberrypi-armv8.rootfs.wic.bz2 | sudo dd of=/dev/sd?
```

7. Create a wpa_supplicant.conf file in the boot fat partition to configure your WIFI.
It uses this https://linux.die.net/man/5/wpa_supplicant.conf format.

```
network={
    ssid="<YOUR NETWORK NAME>"
    psk="<YOUR NETWORK PASSWORD>"
}
```

8. Unmount, remove sd-card, put in Raspberry Pi and boot.

Login with `root` and empty password.
