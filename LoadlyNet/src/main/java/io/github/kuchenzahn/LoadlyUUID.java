package io.github.kuchenzahn;

import java.util.UUID;

public class LoadlyUUID {
    private UUID uuid;

    public LoadlyUUID(String uuid){
        this.uuid = UUID.fromString(uuid);
    }

    public LoadlyUUID(LoadlyUUIDS uuid){
        this.uuid = uuid.getUUID();
    }
    public UUID getUuid() {
        return uuid;
    }

    public static LoadlyUUID fromUUID(UUID uuid){
        return new LoadlyUUID(uuid.toString());
    }

    public enum LoadlyUUIDS {
        SPECIFIC(UUID.fromString("00000000-0000-0000-0000-000000000003")),
        SERVER(UUID.fromString("00000000-0000-0000-0000-000000000000")),
        ALL_PLAYERS(UUID.fromString("00000000-0000-0000-0000-000000000001")),
        EVERYONE(UUID.fromString("00000000-0000-0000-0000-000000000002"));

        private UUID uuid;

        LoadlyUUIDS(UUID uuid){
            this.uuid = uuid;
        }

        public LoadlyUUID get(){
            return new LoadlyUUID(this);
        }

        public UUID getUUID() {
            return uuid;
        }

        public String getUUIDAsString(){
            return uuid.toString();
        }
    }

}
