package com.github.gosvoh;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final Client CLIENT = new Client(CLIENT_BUILDER);

    public static final ForgeConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    /*public static class Common {
        public final ForgeConfigSpec.IntValue blocksNeedToDestroy;
        public final ForgeConfigSpec.IntValue baseExpToGain;
        public final ForgeConfigSpec.IntValue multiplierForLevelStep;
        public final ForgeConfigSpec.IntValue levelStep;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment(Constants.MOD_NAME + " mod configurations")
                    .push(Constants.MOD_ID);

            blocksNeedToDestroy = builder
                    .comment("How many blocks needs to be destroyed to get experience (default: 100)")
                    .worldRestart()
                    .defineInRange("blocks_need_to_destroy", 2, 1, 1000);

            baseExpToGain = builder
                    .comment("How much experience you will get (default: 1)")
                    .worldRestart()
                    .defineInRange("base_experience", 1, 1, 1000);

            multiplierForLevelStep = builder
                    .comment("Multiplier for level step.\n" +
                             "For example:\n" +
                             "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" +
                             "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" +
                             "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" +
                             "etc.\n" +
                             "(default: 2)")
                    .worldRestart()
                    .defineInRange("multiplier_for_level_step", 2, 1, 1000);

            levelStep = builder
                    .comment("How many blocks needs to be destroyed to get experience (default: 5)")
                    .worldRestart()
                    .defineInRange("level_step", 5, 1, 1000);

            builder.pop();
        }
    }*/

    /*public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;*/

    /*static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }*/

    public static class Client {

        public final ForgeConfigSpec.IntValue blocksNeedToDestroy;
        public final ForgeConfigSpec.IntValue baseExpToGain;
        public final ForgeConfigSpec.IntValue multiplierForLevelStep;
        public final ForgeConfigSpec.IntValue levelStep;

        Client(ForgeConfigSpec.Builder builder) {
            String desc;
            builder.comment(Constants.MOD_NAME + " mod configuration").push(Constants.MOD_ID);

            desc = "How many blocks needs to be destroyed to get experience (default: 100)";
            blocksNeedToDestroy = builder.comment(desc)
                    .defineInRange("blocks_need_to_destroy", 2, 1, Integer.MAX_VALUE);

            desc = "How much experience you will get (default: 1)";
            baseExpToGain = builder.comment(desc)
                    .defineInRange("base_experience", 1, 1, 1000);

            desc = "Multiplier for level step.\n" +
                   "For example:\n" +
                   "0-4 lvl you will gain 1 exp every 100 destroyed blocks\n" +
                   "5-9 lvl you will gain 2 exp every 100 destroyed blocks\n" +
                   "10-14 lvl you will gain 4 exp every 100 destroyed blocks\n" +
                   "etc.\n" +
                   "(default: 2)";
            multiplierForLevelStep = builder.comment(desc)
                    .defineInRange("multiplier_for_level_step", 2, 1, 1000);

            desc = "How many blocks needs to be destroyed to get experience (default: 5)";
            levelStep = builder.comment(desc)
                    .defineInRange("level_step", 5, 1, 1000);
        }
    }
}
