package backend

import org.smop.pygments._
import scala.collection.JavaConversions._
import collection.immutable.TreeMap
import scala.math.Ordering.StringOrdering

import java.util.{Map => JMap}

object Pygments {
	private val p = new ForkPygmentize

	implicit private val myPreferredOrdering = CaseInsensitiveOrdering
	val lexers: JMap[String, String] = {
		def buildLexerTuple(l: LexerDefinition) = l.name -> l.aliases.head
		TreeMap(p.allLexers.map(buildLexerTuple).toSeq: _*)
	}

	def highlight(code: String, language: String) = {
		p.highlight(code, Lexer(language), Formatter("html", Map("linenos" -> "table")))
	}
}

private object CaseInsensitiveOrdering extends StringOrdering {
	override def compare(x: String, y: String) = super.compare(x.toLowerCase, y.toLowerCase)
}
