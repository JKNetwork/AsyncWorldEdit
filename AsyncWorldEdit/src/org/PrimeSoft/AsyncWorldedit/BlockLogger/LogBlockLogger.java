/*
 * The MIT License
 *
 * Copyright 2013 SBPrime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.PrimeSoft.AsyncWorldedit.BlockLogger;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import org.PrimeSoft.AsyncWorldedit.PluginMain;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author SBPrime
 */
public class LogBlockLogger implements IBlockLogger {
    /**
     * Log block
     */
    private LogBlock m_logBlock;
    
    
    /**
     * Is the logger enabled
     */
    private boolean m_isEnabled;
    
    
    /**
     * Get instance of the log block plugin
     * @param plugin
     * @return
     */
    public static LogBlock getLogBlock(JavaPlugin plugin) {
        try {
            Plugin cPlugin = plugin.getServer().getPluginManager().getPlugin("LogBlock");

            if ((cPlugin == null) || (!(cPlugin instanceof LogBlock))) {
                return null;
            }           
            
            return (LogBlock) cPlugin;
        } catch (NoClassDefFoundError ex) {
            return null;
        }
    }
    
    public LogBlockLogger(PluginMain plugin)
    {
        m_logBlock = getLogBlock(plugin);
        if (m_logBlock == null)
        {
            PluginMain.Log("Error initializing LogBlock logger.");
            m_isEnabled = false;
        } else {
            PluginMain.Log("LogBlock logger initialized");
            m_isEnabled = true;
        }
    }
    
    
    @Override
    public void LogBlock(Vector location, BaseBlock oldBlock, BaseBlock newBlock, 
        String player, World world) {
        if (!m_isEnabled)
        {
            return;
        }
                
        
        Location l = new Location(world, location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Consumer consumer = m_logBlock.getConsumer();
        consumer.queueBlockBreak(player, l, oldBlock.getType(), (byte) oldBlock.getData());
        consumer.queueBlockPlace(player, l, newBlock.getType(), (byte) newBlock.getData());
    }
    
}
