SUMMARY = "wifi config init"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://config-init.service \
    file://config-init.sh \
    file://wlan.network \
"

FILES:${PN} += "\
    ${systemd_unitdir}/system/config-init.service \
    ${sysconfdir}/systemd/network/wlan.network \
"

RDEPENDS:${PN} += "\
    wpa-supplicant \
"

S = "${UNPACKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} += "config-init.service"

do_install() {
    install -d ${D}${bindir}/
    install -m 0755 ${UNPACKDIR}/config-init.sh ${D}${bindir}/

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${UNPACKDIR}/config-init.service ${D}${systemd_unitdir}/system/
    sed -i  -e 's,@BINDIR@,${bindir},g' \
            ${D}${systemd_unitdir}/system/config-init.service

    install -d -m 0755 ${D}${sysconfdir}/systemd/network
    install -m 0644 ${UNPACKDIR}/wlan.network ${D}${sysconfdir}/systemd/network/

    install -d ${D}${sysconfdir}/wpa_supplicant

    # decided to do here instead of a bbappend of wpa:supplicant
    install -d ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/
    ln -sf /${libdir}/systemd/system/wpa_supplicant@.service ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}
