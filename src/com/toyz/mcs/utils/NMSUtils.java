package com.toyz.mcs.utils;

import java.lang.reflect.*;
import java.util.Collection;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * Contains some raw methods for doing some simple NMS utilities.
 * 
 * This is not meant to be a replacement for full-on NMS or Protocol libs,
 * but it is enough for Magic to use internally without requiring any
 * external dependencies.
 * 
 * Use any of this at your own risk!
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NMSUtils {
    protected static boolean failed = false;

    protected static String versionPrefix = "";

    protected static Class<?> class_ItemStack;
    protected static Class<?> class_NBTBase;
    protected static Class<?> class_NBTTagCompound;
    protected static Class<?> class_NBTTagList;
    protected static Class<?> class_NBTTagByte;
    protected static Class<?> class_NBTTagString;
    protected static Class<?> class_CraftTask;
    protected static Class<?> class_CraftInventoryCustom;
    protected static Class<?> class_CraftItemStack;
    protected static Class<?> class_CraftLivingEntity;
    protected static Class<?> class_Entity;
    protected static Class<?> class_EntityLiving;
    protected static Class<?> class_DataWatcher;
    protected static Class<?> class_World;
    protected static Class<?> class_Packet;
    protected static Class<Enum> class_EnumSkyBlock;
    protected static Class<?> class_PacketPlayOutMapChunkBulk;
    protected static Class<?> class_EntityPainting;
    protected static Class<?> class_EntityItemFrame;
    protected static Class<?> class_EntityMinecartRideable;
    protected static Class<?> class_AxisAlignedBB;

    protected static Method class_NBTTagList_addMethod;
    protected static Method class_NBTTagCompound_setMethod;
    protected static Method class_DataWatcher_watchMethod;
    protected static Method class_World_getEntitiesMethod;
    protected static Method class_Entity_getBukkitEntityMethod;
    protected static Method class_AxisAlignedBB_createBBMethod;
    protected static Method class_World_explodeMethod;
    protected static Method class_NBTTagCompound_setBooleanMethod;
    protected static Method class_NBTTagCompound_setStringMethod;
    protected static Method class_NBTTagCompound_removeMethod;
    protected static Method class_NBTTagCompound_getStringMethod;
    protected static Method class_NBTTagCompound_getMethod;
    protected static Method class_NBTTagCompound_getCompoundMethod;

    protected static Method class_CraftItemStack_copyMethod;
    protected static Method class_CraftItemStack_mirrorMethod;
    protected static Method class_NBTTagCompound_hasKeyMethod;

    protected static Constructor class_NBTTagList_consructor;
    protected static Constructor class_NBTTagList_legacy_consructor;
    protected static Constructor class_CraftInventoryCustom_constructor;
    protected static Constructor class_NBTTagByte_constructor;
    protected static Constructor class_NBTTagByte_legacy_constructor;

    protected static Field class_Entity_invulnerableField;
    protected static Field class_ItemStack_tagField;

    static
    {
        // Find classes Bukkit hides from us. :-D
        // Much thanks to @DPOHVAR for sharing the PowerNBT code that powers the reflection approach.
        String className = Bukkit.getServer().getClass().getName();
        String[] packages = className.split("\\.");
        if (packages.length == 5) {
            versionPrefix = packages[3] + ".";
        }
        try {
            class_EntityLiving = fixBukkitClass("net.minecraft.server.EntityLiving");
            class_Entity = fixBukkitClass("net.minecraft.server.Entity");
            class_ItemStack = fixBukkitClass("net.minecraft.server.ItemStack");
            class_DataWatcher = fixBukkitClass("net.minecraft.server.DataWatcher");
            class_NBTBase = fixBukkitClass("net.minecraft.server.NBTBase");
            class_NBTTagCompound = fixBukkitClass("net.minecraft.server.NBTTagCompound");
            class_NBTTagList = fixBukkitClass("net.minecraft.server.NBTTagList");
            class_NBTTagString = fixBukkitClass("net.minecraft.server.NBTTagString");
            class_NBTTagByte = fixBukkitClass("net.minecraft.server.NBTTagByte");
            class_CraftInventoryCustom = fixBukkitClass("org.bukkit.craftbukkit.inventory.CraftInventoryCustom");
            class_CraftItemStack = fixBukkitClass("org.bukkit.craftbukkit.inventory.CraftItemStack");
            class_CraftTask = fixBukkitClass("org.bukkit.craftbukkit.scheduler.CraftTask");
            class_CraftLivingEntity = fixBukkitClass("org.bukkit.craftbukkit.entity.CraftLivingEntity");
            class_Packet = fixBukkitClass("net.minecraft.server.Packet");
            class_World = fixBukkitClass("net.minecraft.server.World");
            class_EnumSkyBlock = (Class<Enum>)fixBukkitClass("net.minecraft.server.EnumSkyBlock");
            class_EntityPainting = fixBukkitClass("net.minecraft.server.EntityPainting");
            class_EntityItemFrame = fixBukkitClass("net.minecraft.server.EntityItemFrame");
            class_EntityMinecartRideable = fixBukkitClass("net.minecraft.server.EntityMinecartRideable");
            class_AxisAlignedBB = fixBukkitClass("net.minecraft.server.AxisAlignedBB");

            class_NBTTagList_addMethod = class_NBTTagList.getMethod("add", class_NBTBase);
            class_NBTTagCompound_setMethod = class_NBTTagCompound.getMethod("set", String.class, class_NBTBase);
            class_DataWatcher_watchMethod = class_DataWatcher.getMethod("watch", Integer.TYPE, Object.class);
            class_World_getEntitiesMethod = class_World.getMethod("getEntities", class_Entity, class_AxisAlignedBB);
            class_Entity_getBukkitEntityMethod = class_Entity.getMethod("getBukkitEntity");
            class_AxisAlignedBB_createBBMethod = class_AxisAlignedBB.getMethod("a", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
            class_World_explodeMethod = class_World.getMethod("createExplosion", class_Entity, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Boolean.TYPE, Boolean.TYPE);
            class_NBTTagCompound_setBooleanMethod = class_NBTTagCompound.getMethod("setBoolean", String.class, Boolean.TYPE);
            class_NBTTagCompound_setStringMethod = class_NBTTagCompound.getMethod("setString", String.class, String.class);
            class_NBTTagCompound_removeMethod = class_NBTTagCompound.getMethod("remove", String.class);
            class_NBTTagCompound_getStringMethod = class_NBTTagCompound.getMethod("getString", String.class);
            class_CraftItemStack_copyMethod = class_CraftItemStack.getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class);
            class_CraftItemStack_mirrorMethod = class_CraftItemStack.getMethod("asCraftMirror", class_ItemStack);
            class_NBTTagCompound_hasKeyMethod = class_NBTTagCompound.getMethod("hasKey", String.class);
            class_NBTTagCompound_getMethod = class_NBTTagCompound.getMethod("get", String.class);
            class_NBTTagCompound_getCompoundMethod = class_NBTTagCompound.getMethod("getCompound", String.class);

            class_CraftInventoryCustom_constructor = class_CraftInventoryCustom.getConstructor(InventoryHolder.class, Integer.TYPE, String.class);

            class_Entity_invulnerableField = class_Entity.getDeclaredField("invulnerable");
            class_Entity_invulnerableField.setAccessible(true);
            class_ItemStack_tagField = class_ItemStack.getField("tag");
        }
        catch (Throwable ex) {
            failed = true;
            ex.printStackTrace();
        }

        try {
            class_NBTTagList_legacy_consructor = class_NBTTagString.getConstructor(String.class, String.class);
            class_NBTTagByte_legacy_constructor = class_NBTTagByte.getConstructor(String.class, Byte.TYPE);
        }
        catch (Throwable legacy) {
            try {
                class_NBTTagList_consructor = class_NBTTagString.getConstructor(String.class);
                class_NBTTagByte_constructor = class_NBTTagByte.getConstructor(Byte.TYPE);
            }
            catch (Throwable ex) {
                failed = true;
                ex.printStackTrace();
            }
        }

        class_PacketPlayOutMapChunkBulk = getVersionedBukkitClass("net.minecraft.server.PacketPlayOutMapChunkBulk", "net.minecraft.server.Packet56MapChunkBulk");
    }

    public static boolean getFailed() {
        return failed;
    }

    public static Class<?> getVersionedBukkitClass(String newVersion, String oldVersion) {
        Class<?> c = getBukkitClass(newVersion);
        if (c == null) {
            c = getBukkitClass(oldVersion);
            if (c == null) {
                System.err.println("Could not bind to " + newVersion + " or " + oldVersion);
            }
        }
        return c;
    }

    public static Class<?> getBukkitClass(String className) {
        Class<?> ret = null;
        try {
            ret = fixBukkitClass(className);
        }
        catch (Throwable ex) {
        }
        return ret;
    }

    public static Class<?> fixBukkitClass(String className) {
        className = className.replace("org.bukkit.craftbukkit.", "org.bukkit.craftbukkit." + versionPrefix);
        className = className.replace("net.minecraft.server.", "net.minecraft.server." + versionPrefix);
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Object getHandle(org.bukkit.inventory.ItemStack stack) {
        Object handle = null;
        try {
            Field handleField = stack.getClass().getDeclaredField("handle");
            handleField.setAccessible(true);
            handle = handleField.get(stack);
        } catch (Throwable ex) {
            handle = null;
        }
        return handle;
    }

    public static Object getHandle(org.bukkit.World world) {
        Object handle = null;
        try {
            Method handleMethod = world.getClass().getMethod("getHandle");
            handle = handleMethod.invoke(world);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return handle;
    }

    public static Object getHandle(org.bukkit.entity.Entity entity) {
        Object handle = null;
        try {
            Method handleMethod = entity.getClass().getMethod("getHandle");
            handle = handleMethod.invoke(entity);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return handle;
    }

    public static boolean isDone(org.bukkit.Chunk chunk) {
        Object chunkHandle = getHandle(chunk);
        boolean done = false;
        try {
            Field doneField = chunkHandle.getClass().getDeclaredField("done");
            doneField.setAccessible(true);
            done = (Boolean)doneField.get(chunkHandle);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return done;
    }

    public static Object getHandle(org.bukkit.Chunk chunk) {
        Object handle = null;
        try {
            Method handleMethod = chunk.getClass().getMethod("getHandle");
            handle = handleMethod.invoke(chunk);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return handle;
    }

    public static Object getHandle(org.bukkit.entity.Player player) {
        Object handle = null;
        try {
            Method handleMethod = player.getClass().getMethod("getHandle");
            handle = handleMethod.invoke(player);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return handle;
    }

    protected static Object getHandle(Object object) {
        Object handle = null;
        try {
            Method handleMethod = object.getClass().getMethod("getHandle");
            handle = handleMethod.invoke(object);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return handle;
    }

    protected static void sendPacket(Location source, Collection<Player> players, Object packet) throws Exception  {
        players = ((players != null && players.size() > 0) ? players : source.getWorld().getPlayers());

        int viewDistanceSquared = Bukkit.getServer().getViewDistance() * Bukkit.getServer().getViewDistance();
        for(Player p1 : players) {
            if(p1.getLocation().distanceSquared(source) <= viewDistanceSquared) {
                sendPacket(p1, packet);
            }
        }
    }

    protected static void sendPacket(Player player, Object packet) throws Exception {
        Object playerHandle = getHandle(player);
        Field connectionField = playerHandle.getClass().getField("playerConnection");
        Object connection = connectionField.get(playerHandle);
        Method sendPacketMethod = connection.getClass().getMethod("sendPacket", class_Packet);
        sendPacketMethod.invoke(connection, packet);
    }
    
    public static int getFacing(BlockFace direction)
    {
        int dir;
        switch (direction) {
        case SOUTH:
        default:
            dir = 0;
            break;
        case WEST:
            dir = 1;
            break;
        case NORTH:
            dir = 2;
            break;
        case EAST:
            dir = 3;
            break;
        }
        
        return dir;
    }

    public static org.bukkit.entity.Entity getBukkitEntity(Object entity)
    {
        if (entity == null) return null;
        try {
            Method getMethod = entity.getClass().getMethod("getBukkitEntity");
            Object bukkitEntity = getMethod.invoke(entity);
            if (!(bukkitEntity instanceof org.bukkit.entity.Entity)) return null;
            return (org.bukkit.entity.Entity)bukkitEntity;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Object getTag(Object mcItemStack) {
        Object tag = null;
        try {
            tag = class_ItemStack_tagField.get(mcItemStack);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return tag;
    }

    protected static Object getNMSCopy(ItemStack stack) {
        Object nms = null;
        try {
            nms = class_CraftItemStack_copyMethod.invoke(null, stack);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return nms;
    }

    public static ItemStack getCopy(ItemStack stack) {
        if (stack == null) return null;

        try {
            Object craft = getNMSCopy(stack);
            stack = (ItemStack)class_CraftItemStack_mirrorMethod.invoke(null, craft);
        } catch (Throwable ex) {
            stack = null;
        }

        return stack;
    }

    public static ItemStack makeReal(ItemStack stack) {
        if (stack == null) return null;
        Object nmsStack = getHandle(stack);
        if (nmsStack == null) {
            stack = getCopy(stack);
            nmsStack = getHandle(stack);
        }
        if (nmsStack == null) {
            return null;
        }
        try {
            Object tag = class_ItemStack_tagField.get(nmsStack);
            if (tag == null) {
                class_ItemStack_tagField.set(nmsStack, class_NBTTagCompound.newInstance());
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }

        return stack;
    }

    public static String getMeta(ItemStack stack, String tag, String defaultValue) {
        String result = getMeta(stack, tag);
        return result == null ? defaultValue : result;
    }

    public static boolean hasMeta(ItemStack stack, String tag) {
        return getNode(stack, tag) != null;
    }

    public static Object getNode(ItemStack stack, String tag) {
        if (stack == null) return null;
        Object meta = null;
        try {
            Object craft = getHandle(stack);
            if (craft == null) return null;
            Object tagObject = getTag(craft);
            if (tagObject == null) return null;
            meta = class_NBTTagCompound_getMethod.invoke(tagObject, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return meta;
    }

    public static boolean containsNode(Object nbtBase, String tag) {
        if (nbtBase == null) return false;
        Boolean result = false;
        try {
            result = (Boolean)class_NBTTagCompound_hasKeyMethod.invoke(nbtBase, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static Object getNode(Object nbtBase, String tag) {
        if (nbtBase == null) return null;
        Object meta = null;
        try {
            meta = class_NBTTagCompound_getMethod.invoke(nbtBase, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return meta;
    }

    public static Object createNode(Object nbtBase, String tag) {
        if (nbtBase == null) return null;
        Object meta = null;
        try {
            meta = class_NBTTagCompound_getCompoundMethod.invoke(nbtBase, tag);
            class_NBTTagCompound_setMethod.invoke(nbtBase, tag, meta);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return meta;
    }

    public static Object createNode(ItemStack stack, String tag) {
        if (stack == null) return null;
        Object outputObject = getNode(stack, tag);
        if (outputObject == null) {
            try {
                Object craft = getHandle(stack);
                if (craft == null) return null;
                Object tagObject = getTag(craft);
                if (tagObject == null) return null;
                outputObject = class_NBTTagCompound.newInstance();
                class_NBTTagCompound_setMethod.invoke(tagObject, tag, outputObject);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return outputObject;
    }

    public static String getMeta(Object node, String tag, String defaultValue) {
        String meta = getMeta(node, tag);
        return meta == null || meta.length() == 0 ? defaultValue : meta;
    }

    public static String getMeta(Object node, String tag) {
        if (node == null || !class_NBTTagCompound.isInstance(node)) return null;
        String meta = null;
        try {
            meta = (String)class_NBTTagCompound_getStringMethod.invoke(node, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return meta;
    }

    public static void setMeta(Object node, String tag, String value) {
        if (node == null|| !class_NBTTagCompound.isInstance(node)) return;
        try {
            if (value == null || value.length() == 0) {
                class_NBTTagCompound_removeMethod.invoke(node, tag);
            } else {
                class_NBTTagCompound_setStringMethod.invoke(node, tag, value);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void removeMeta(Object node, String tag) {
        if (node == null|| !class_NBTTagCompound.isInstance(node)) return;
        try {
            class_NBTTagCompound_removeMethod.invoke(node, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static String getMeta(ItemStack stack, String tag) {
        if (stack == null) return null;
        String meta = null;
        try {
            Object craft = getHandle(stack);
            if (craft == null) return null;
            Object tagObject = getTag(craft);
            if (tagObject == null) return null;
            meta = (String)class_NBTTagCompound_getStringMethod.invoke(tagObject, tag);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return meta;
    }

    public static void setMeta(ItemStack stack, String tag, String value) {
        if (stack == null) return;
        try {
            Object craft = getHandle(stack);
            if (craft == null) return;
            Object tagObject = getTag(craft);
            if (tagObject == null) return;
            class_NBTTagCompound_setStringMethod.invoke(tagObject, tag, value);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static void addGlow(ItemStack stack) {
        if (stack == null) return;

        try {
            Object craft = getHandle(stack);
            if (craft == null) return;
            Object tagObject = getTag(craft);
            if (tagObject == null) return;
            final Object enchList = class_NBTTagList.newInstance();
            class_NBTTagCompound_setMethod.invoke(tagObject, "ench", enchList);

            // Testing Glow API based on ItemMetadata storage
            Object bukkitData = createNode(stack, "bukkit");
            class_NBTTagCompound_setBooleanMethod.invoke(bukkitData, "glow", true);
        } catch (Throwable ex) {

        }
    }

    public static void makeUnbreakable(ItemStack stack) {
        if (stack == null) return;

        try {
            Object craft = getHandle(stack);
            if (craft == null) return;
            Object tagObject = getTag(craft);
            if (tagObject == null) return;

            Object unbreakableFlag = null;
            if (class_NBTTagByte_constructor != null) {
                unbreakableFlag = class_NBTTagByte_constructor.newInstance((byte) 1);
            } else {
                unbreakableFlag = class_NBTTagByte_legacy_constructor.newInstance("", (byte) 1);
            }
            class_NBTTagCompound_setMethod.invoke(tagObject, "Unbreakable", unbreakableFlag);
        } catch (Throwable ex) {

        }
    }

    public static void hideFlags(ItemStack stack) {
        if (stack == null) return;

        try {
            Object craft = getHandle(stack);
            if (craft == null) return;
            Object tagObject = getTag(craft);
            if (tagObject == null) return;

            Object hideFlag = null;
            if (class_NBTTagByte_constructor != null) {
                hideFlag = class_NBTTagByte_constructor.newInstance((byte) 63);
            } else {
                hideFlag = class_NBTTagByte_legacy_constructor.newInstance("", (byte) 63);
            }
            class_NBTTagCompound_setMethod.invoke(tagObject, "HideFlags", hideFlag);
        } catch (Throwable ex) {

        }
    }

    public static boolean createExplosion(Entity entity, World world, double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        boolean result = false;
        if (world == null) return false;
        try {
            Object worldHandle = getHandle(world);
            if (worldHandle == null) return false;
            Object entityHandle = entity == null ? null : getHandle(entity);

            Object explosion = class_World_explodeMethod.invoke(worldHandle, entityHandle, x, y, z, power, setFire, breakBlocks);
            Field cancelledField = explosion.getClass().getDeclaredField("wasCanceled");
            result = (Boolean)cancelledField.get(explosion);
        } catch (Throwable ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public static void makeTemporary(ItemStack itemStack, String message) {
        setMeta(itemStack, "temporary", message);
    }

    public static boolean isTemporary(ItemStack itemStack) {
        return hasMeta(itemStack, "temporary");
    }

    public static String getTemporaryMessage(ItemStack itemStack) {
        return getMeta(itemStack, "temporary");
    }
}