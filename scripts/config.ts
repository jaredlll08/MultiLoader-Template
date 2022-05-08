import {Config} from "./types"
import path from 'path';

//TODO This will only work on a windows machine due to the path, need a good way to get the correct separator char into the map.
export default {
    lineReplacements: {
        "gradle.properties": [
            {
                regex: /((minecraft_version|minecraftVersion)=)(.*)/,
                replacement: (old, matches, cache) => {

                    return matches.at(1) + cache.mcVersion;
                }
            },
            {
                regex: /((forge_version|forgeVersion)=)(.*)/,
                replacement: (old, matches, cache) => {

                    return matches.at(1) + cache.forgeVersions.promos[`${cache.mcVersion}-latest`];
                }
            },
            {
                regex: /((fabric_loader_version|fabricLoaderVersion)=)(.*)/,
                replacement: (old, matches, cache) => {

                    return matches.at(1) + cache.fabricLoaderVersions[0].loader.version;
                }
            },
            {
                regex: /((fabric_version|fabricVersion)=)(.*)/,
                replacement: (old, matches, cache) => {

                    return matches.at(1) + cache.fabricVersion;
                }
            }
        ],
        "Fabric\\src\\main\\resources\\fabric.mod.json": [{
            regex: /.*("fabricloader": ">=(.*)").*/,
            replacement: (old, matches, cache) => {

                return old.replace(matches[2], cache.fabricLoaderVersions[0].loader.version);
            }
        }]
    }
} as Config