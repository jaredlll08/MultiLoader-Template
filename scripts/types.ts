export interface Config {
    lineReplacements: {
        [name: string]: LineReplacement[]
    }
}

export interface Cache {
    mcVersion: string;
    kotlinVersion: string;
    forgeVersions: { promos: { [key: string]: string } };
    forgeKotlinVersion: string;
    fabricLoaderVersions: { loader: { version: string } }[]
    fabricVersion: string
    fabricKotlinVersion: string;
}

export interface LineReplacement {
    regex: string | RegExp,
    replacement: (old: string, matches: RegExpMatchArray, cache: Cache) => string
}