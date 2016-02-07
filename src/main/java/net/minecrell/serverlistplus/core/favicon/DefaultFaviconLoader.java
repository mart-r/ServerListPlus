/*
 *        _____                     __    _     _   _____ _
 *       |   __|___ ___ _ _ ___ ___|  |  |_|___| |_|  _  | |_ _ ___
 *       |__   | -_|  _| | | -_|  _|  |__| |_ -|  _|   __| | | |_ -|
 *       |_____|___|_|  \_/|___|_| |_____|_|___|_| |__|  |_|___|___|
 *
 *  ServerListPlus - http://git.io/slp
 *  Copyright (c) 2014, Minecrell <https://github.com/Minecrell>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.minecrell.serverlistplus.core.favicon;

import com.google.common.io.BaseEncoding;
import net.minecrell.serverlistplus.core.ServerListPlusCore;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;

public enum DefaultFaviconLoader implements FaviconLoader {
    FILE {
        @Override
        public BufferedImage load(ServerListPlusCore core, String path) throws IOException {
            try (InputStream in = Files.newInputStream(core.getPlugin().getPluginFolder().resolve(path))) {
                return FaviconHelper.fromStream(in);
            }
        }
    }, URL {
        @Override
        public BufferedImage load(ServerListPlusCore core, String url) throws IOException {
            return FaviconHelper.fromURL(core, new URL(url));
        }
    }, SKIN_HEAD {
        @Override
        public BufferedImage load(ServerListPlusCore core, String name) throws IOException {
            return FaviconHelper.fromSkin(core, name, false);
        }
    }, SKIN_HELM {
        @Override
        public BufferedImage load(ServerListPlusCore core, String name) throws IOException {
            return FaviconHelper.fromSkin(core, name, true);
        }
    }, BASE64 { // Encoded favicon
        @Override
        public BufferedImage load(ServerListPlusCore core, String base64) throws IOException {
            return FaviconHelper.fromStream(BaseEncoding.base64().decodingStream(new StringReader(base64)));
        }
    }
}