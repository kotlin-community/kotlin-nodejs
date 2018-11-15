package Node.Path

import Node.Path.Internal.FormatInputPathObject
import Node.Path.Internal.delimiter as _delimiter
import Node.Path.Internal.sep as _sep
import Node.Path.Internal.isAbsolute as _isAbsolute
import Node.Path.Internal.dirname as _dirname
import Node.Path.Internal.basename as _basename
import Node.Path.Internal.extname as _extname
import Node.Path.Internal.join as _join
import Node.Path.Internal.normalize as _normalize
import Node.Path.Internal.resolve as _resolve
import Node.Path.Internal.relative as _relative
import Node.Path.Internal.parse as _parse
import Node.Path.Internal.format as _format

// TODO: add ability to destructure the path
class Path(value: String = "") {
    companion object {
        val separator: String
            get() = _sep
        val delimiter: String
            get() = _delimiter
    }

    private var value: String = _normalize(value)

    val directory: String
        get() = _dirname(this.value)
    val filename: String
        get() = _basename(this.value)
    val extension: String
        get() = _extname(this.value)
    val isAbsolute: Boolean
        get() = _isAbsolute(this.value)

    constructor(vararg segments: String): this() {
        this.value = _normalize(_join(*segments))
    }

    constructor(parsedPath: ParsedPath): this() {
        this.value = _format(object : FormatInputPathObject {
            override var root = parsedPath.root as String?
            override var dir = parsedPath.directory as String?
            override var base = parsedPath.filename as String?
            override var ext = parsedPath.extension as String?
            override var name = parsedPath.name as String?
        })
    }

    fun join(vararg segments: String): Path {
        this.value = _normalize(_join(this.value, *segments))
        return this
    }

    fun resolve(vararg segments: String = emptyArray()): Path {
        this.join(*segments)
        this.value = _resolve(this.value)
        return this
    }

    fun relativeTo(target: Path): Path {
        return Path(_relative(this.value, target.value))
    }

    fun parse(): ParsedPath {
        val parsedPath = _parse(this.value)
        return ParsedPath(
                root = parsedPath.root,
                directory = parsedPath.dir,
                filename = parsedPath.base,
                extension = parsedPath.ext,
                name = parsedPath.name
        )
    }

    /**
     * Get a Node compatible string a path
     */
    override fun toString(): String {
        return this.value
    }

    /**
     * Returns directory component
     */
    operator fun component1(): String {
        return this.directory
    }

    /**
     * Returns filename component
     */
    operator fun component2(): String {
        return this.filename
    }

    /**
     * Returns extension component
     */
    operator fun component3(): String {
        return this.extension
    }

    /**
     * Returns boolean indicating if path is absolute
     */
    operator fun component4(): Boolean {
        return this.isAbsolute
    }

}