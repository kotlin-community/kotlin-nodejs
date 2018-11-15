@file:JsModule("path")

package node.path

internal external interface ParsedPath {
    var root: String
    var dir: String
    var base: String
    var ext: String
    var name: String
}
internal external interface FormatInputPathObject {
    var root: String?
    var dir: String?
    var base: String?
    var ext: String?
    var name: String?
}
internal external fun normalize(p: String): String = definedExternally
internal external fun join(vararg paths: String): String = definedExternally
internal external fun resolve(vararg pathSegments: String): String = definedExternally
internal external fun isAbsolute(path: String): Boolean = definedExternally
internal external fun relative(from: String, to: String): String = definedExternally
internal external fun dirname(p: String): String = definedExternally
internal external fun basename(p: String, ext: String? = definedExternally /* null */): String = definedExternally
internal external fun extname(p: String): String = definedExternally
internal external var sep: String /* String /* "\" */ | String /* "/" */ */ = definedExternally
internal external var delimiter: String /* String /* ";" */ | String /* ":" */ */ = definedExternally
internal external fun parse(pathString: String): ParsedPath = definedExternally
internal external fun format(pathObject: FormatInputPathObject): String = definedExternally

