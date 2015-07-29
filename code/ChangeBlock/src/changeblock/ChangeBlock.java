
package changeblock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.effects.Particle;
import net.canarymod.api.world.effects.Particle.Type;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.effects.SoundEffect;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.world.World;
import com.pragprog.ahmine.ez.EZPlugin;


public class ChangeBlock extends EZPlugin {

 // Checks for correct number of arguments
  private void parseArgs(Player me, String [] args) {
    if (args.length != 1) {
      usage(me);
      return;
    }
    else {
      switchBlock(me, args);
    }
  }

  // If too few or too many arguments, return this statement to chat
  private void usage(Player me) {
    me.chat("Usage: /changeblock blocktype");
  }

  //
  private void switchBlock(Player me, String [] args) {

    BlockIterator sightItr = new BlockIterator(new LineTracer(me), true);
    String type = args[0].toUpperCase();
    BlockType t = BlockType.fromString(type);
    while (sightItr.hasNext()) {
      Block b = sightItr.next();
      if (b.getType() != BlockType.Air) {
        b.getWorld().setBlockAt(b.getLocation(), t);
        playSound(b.getLocation(), SoundEffect.Type.EXPLODE);
        break;
      }
    }
  }

  @Command(aliases = { "changeblock" },
            description = "Change first block in path",
            permissions = { "" },
            toolTip = "/changeblock <blocktype>")
  public void changeblockCommand(MessageReceiver caller, String[] args) {
    if (caller instanceof Player) {
      Player me = (Player)caller;
      parseArgs(me, args);

    }
  }
}
