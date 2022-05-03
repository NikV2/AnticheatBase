package me.nik.anticheatbase.utils.versionutils;

/**
 * Client Version.
 * This is a nice tool for minecraft's protocol versions.
 * You won't have to memorize the protocol version, just memorize the client version
 * as the version you see in the minecraft launcher.
 *
 * @author Retrooper
 */
public enum ClientVersion {
    v_1_7_10(5),
    v_1_8(47),
    v_1_9(107),
    v_1_9_1(108),
    v_1_9_2(109),
    v_1_9_3(110),
    v_1_10(210),
    v_1_11(315),
    v_1_11_1(316),
    v_1_12(335),
    v_1_12_1(338),
    v_1_12_2(340),
    v_1_13(393),
    v_1_13_1(401),
    v_1_13_2(404),
    v_1_14(477),
    v_1_14_1(480),
    v_1_14_2(485),
    v_1_14_3(490),
    v_1_14_4(498),
    v_1_15(573),
    v_1_15_1(575),
    v_1_15_2(578),
    v_1_16(735),
    v_1_16_1(736),
    v_1_16_2(751),
    v_1_16_3(753),
    v_1_16_4(754),
    v_1_17(755),
    v_1_17_1(756),
    v_1_18(757),
    v1_18_2(758),
    UNKNOWN(Integer.MAX_VALUE);

    private final int protocolVersion;

    ClientVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * Get a ClientVersion enum by protocol version.
     *
     * @param protocolVersion The protocol version integer
     * @return ClientVersion
     */
    public static ClientVersion getClientVersion(int protocolVersion) {

        for (ClientVersion version : values()) {

            if (protocolVersion == version.protocolVersion) return version;
        }

        return UNKNOWN;
    }

    /**
     * Protocol version of this client version.
     *
     * @return Protocol version.
     */
    public int getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * Is this client version newer than the compared client version?
     * This method simply checks if this client version's protocol version is greater than
     * the compared client version's protocol version.
     *
     * @param target Compared client version.
     * @return Is this client version newer than the compared client version.
     */
    public boolean isHigherThan(ClientVersion target) {
        return protocolVersion > target.protocolVersion;
    }

    /**
     * Is this client version newer than or equal to the compared client version?
     * This method simply checks if this client version's protocol version is newer than or equal to
     * the compared client version's protocol version.
     *
     * @param target Compared client version.
     * @return Is this client version newer than or equal to the compared client version.
     */
    public boolean isHigherThanOrEquals(ClientVersion target) {
        return protocolVersion >= target.protocolVersion;
    }

    /**
     * Is this client version older than the compared client version?
     * This method simply checks if this client version's protocol version is less than
     * the compared client version's protocol version.
     *
     * @param target Compared client version.
     * @return Is this client version older than the compared client version.
     */
    public boolean isLowerThan(ClientVersion target) {
        return protocolVersion < target.protocolVersion;
    }

    /**
     * Is this client version older than or equal to the compared client version?
     * This method simply checks if this client version's protocol version is older than or equal to
     * the compared client version's protocol version.
     *
     * @param target Compared client version.
     * @return Is this client version older than or equal to the compared client version.
     */
    public boolean isLowerThanOrEquals(ClientVersion target) {
        return protocolVersion <= target.protocolVersion;
    }

    /**
     * Is this client version equal to the compared client version.
     * This method simply checks if this client version's protocol version
     * is equal to the compared client version's protocol version.
     *
     * @param target Compared
     * @return Is this client version equal to the compared client version.
     */
    public boolean equals(ClientVersion target) {
        return protocolVersion == target.protocolVersion;
    }

    @Override
    public String toString() {
        return this.name().substring(2).replace("_", ".");
    }
}