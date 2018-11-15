package node.path

import node.path.delimiter as _delimiter
import node.path.sep as _sep
import node.path.isAbsolute as _isAbsolute
import node.path.dirname as _dirname
import node.path.basename as _basename
import node.path.extname as _extname
import node.path.join as _join
import node.path.normalize as _normalize
import node.path.resolve as _resolve
import node.path.relative as _relative
import node.path.parse as _parse
import node.path.format as _format

class Path(value: String = "") {
    enum class Bias {
        Start,
        End,
        All
    }

    companion object {
        val separator: String
            get() = _sep

        val delimiter: String
            get() = _delimiter

        fun join(vararg segments: Array<out String>): Path {} // TODO: implmement

        fun join(vararg segments: Array<out Path>): Path {} // TODO: implmement

        fun resolve(vararg segments: Array<out String>): Path {} // TODO: implmement

        fun resolve(vararg segments: Array<out Path>): Path {} // TODO: implmement

        fun relative(from: String, to: String): Path {} // TODO: implmement

        fun relative(from: Path, to: Path): Path {} // TODO: implmement
    }

    var directory: String = "" // TODO: implmement
    var filename: String = "" // TODO: implmement
    var extension: String = "" // TODO: implmement
    var isAbsolute: Boolean = false // TODO: implmement

    constructor(vararg segments: String): this() {
        // TODO: implmement
    }

    constructor(path: Path): this() {
        // TODO: implmement
    }

    fun append(vararg segments: Array<out String>): Path {
        // TODO: implmement
        return this
    }

    fun append(vararg segments: Array<out Path>): Path {
        // TODO: implmement
        return this
    }

    fun prepend(vararg segments: Array<out String>): Path {
        // TODO: implmement
        return this
    }

    fun prepend(vararg segments: Array<out Path>): Path {
        // TODO: implmement
        return this
    }

    fun remove(target: String, bias: Path.Bias): Path {
        // TODO: implmement
    }

    fun remove(target: Path, bias: Path.Bias): Path {
        // TODO: implmement
    }

    fun toAbsolute(): Path {
        // TODO: implmement
        return this
    }

    fun relativeTo(target: String): Path {
        // TODO: implmement
        return Path()
    }


    fun relativeTo(target: Path): Path {
        // TODO: implmement
        return Path()
    }

    /**
     * Get a node compatible string a path
     */
    override fun toString(): String {
        // TODO: implmement
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