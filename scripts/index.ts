import fs from 'fs';
import path from 'path';
import config from './config.js'
import os from 'os';
import {Cache} from './types.js';
import axios from 'axios';
import {parseStringPromise} from 'xml2js';
import {filterIgnoredFiles, collectFiles} from './util.js';

const cache: Cache = <Cache>{};

const initCache = async () => {
    cache.mcVersion = "1.18.2"
    cache.forgeVersions = await axios.get("https://files.minecraftforge.net/net/minecraftforge/forge/promotions_slim.json").then(value => value.data).catch(reason => reason);
    cache.fabricLoaderVersions = await axios.get(`https://meta.fabricmc.net/v2/versions/loader/${cache.mcVersion}`).then(value => value.data).catch(reason => reason);
    cache.fabricVersion = await axios.get("https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/maven-metadata.xml").then(async value => (await parseStringPromise(value.data)).metadata.versioning[0].latest[0]).catch(reason => reason);
}

const performReplacements = () => {
    const templateFolders = fs.readdirSync(".").filter(file => fs.lstatSync(file).isDirectory()).filter(filterIgnoredFiles);

    for (let template of templateFolders) {
        const files: string[] = [];
        collectFiles(template, files);
        files.forEach(value => {
            const relative = path.relative(template, value);
            const oldContents = fs.readFileSync(value, "utf-8").split(/\r?\n/);
            let newContents = "";

            if (relative in config.lineReplacements) {
                for (let line of oldContents) {
                    let replaced = false;
                    for (let replacement of config.lineReplacements[relative]) {
                        const matches = line.match(replacement.regex);
                        if (matches) {
                            newContents += replacement.replacement(line, matches, cache) + os.EOL;
                            replaced = true;
                            break;
                        }
                    }
                    if (!replaced) {
                        newContents += line += os.EOL;
                    }
                }
            } else {
                newContents = oldContents.join(os.EOL);
            }
            fs.writeFileSync(value, newContents);
        })
    }
}

await initCache();
await performReplacements();