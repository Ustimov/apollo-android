// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.unique_type_name

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.OperationName
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.ResponseFieldMapper
import com.apollographql.apollo.api.ResponseFieldMarshaller
import com.apollographql.apollo.api.ResponseReader
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.internal.SimpleOperationResponseParser
import com.apollographql.apollo.internal.QueryDocumentMinifier
import com.example.unique_type_name.fragment.HeroDetails
import com.example.unique_type_name.type.Episode
import kotlin.Any
import kotlin.Array
import kotlin.Double
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
class HeroDetailQuery : Query<HeroDetailQuery.Data, HeroDetailQuery.Data, Operation.Variables> {
  override fun operationId(): String = OPERATION_ID
  override fun queryDocument(): String = QUERY_DOCUMENT
  override fun wrapData(data: Data?): Data? = data
  override fun variables(): Operation.Variables = Operation.EMPTY_VARIABLES
  override fun name(): OperationName = OPERATION_NAME
  override fun responseFieldMapper(): ResponseFieldMapper<Data> = ResponseFieldMapper {
    Data(it)
  }

  override fun parse(response: Map<String, Any>, scalarTypeAdapters: ScalarTypeAdapters):
      Response<Data> = SimpleOperationResponseParser.parse(response, this, scalarTypeAdapters)

  interface HeroDetailQueryCharacter {
    fun marshaller(): ResponseFieldMarshaller
  }

  data class Friend1(
    val __typename: String,
    val fragments: Fragments
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeString(RESPONSE_FIELDS[0], __typename)
      fragments.marshaller().marshal(it)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("__typename", "__typename", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): Friend1 {
        val __typename = reader.readString(RESPONSE_FIELDS[0])
        val fragments = reader.readConditional(RESPONSE_FIELDS[1]) { conditionalType, reader ->
          val heroDetails = HeroDetails(reader)
          Fragments(
            heroDetails = heroDetails
          )
        }

        return Friend1(
          __typename = __typename,
          fragments = fragments
        )
      }
    }

    data class Fragments(
      val heroDetails: HeroDetails
    ) {
      fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
        heroDetails.marshaller().marshal(it)
      }
    }
  }

  data class Friend(
    val __typename: String,
    /**
     * The name of the character
     */
    val name: String,
    /**
     * The movies this character appears in
     */
    val appearsIn: List<Episode?>,
    /**
     * The friends of the character, or an empty list if they have none
     */
    val friends: List<Friend1?>?
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeString(RESPONSE_FIELDS[0], __typename)
      it.writeString(RESPONSE_FIELDS[1], name)
      it.writeList(RESPONSE_FIELDS[2], appearsIn) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeString(value?.rawValue)
        }
      }
      it.writeList(RESPONSE_FIELDS[3], friends) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeObject(value?.marshaller())
        }
      }
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null),
          ResponseField.forList("appearsIn", "appearsIn", null, false, null),
          ResponseField.forList("friends", "friends", null, true, null)
          )

      operator fun invoke(reader: ResponseReader): Friend {
        val __typename = reader.readString(RESPONSE_FIELDS[0])
        val name = reader.readString(RESPONSE_FIELDS[1])
        val appearsIn = reader.readList<Episode>(RESPONSE_FIELDS[2]) {
          it.readString()?.let{ Episode.safeValueOf(it) }
        }
        val friends = reader.readList<Friend1>(RESPONSE_FIELDS[3]) {
          it.readObject<Friend1> { reader ->
            Friend1(reader)
          }

        }
        return Friend(
          __typename = __typename,
          name = name,
          appearsIn = appearsIn,
          friends = friends
        )
      }
    }
  }

  data class AsHuman(
    val __typename: String,
    /**
     * What this human calls themselves
     */
    val name: String,
    /**
     * This human's friends, or an empty list if they have none
     */
    val friends: List<Friend?>?,
    /**
     * Height in the preferred unit, default is meters
     */
    val height: Double?
  ) : HeroDetailQueryCharacter {
    override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeString(RESPONSE_FIELDS[0], __typename)
      it.writeString(RESPONSE_FIELDS[1], name)
      it.writeList(RESPONSE_FIELDS[2], friends) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeObject(value?.marshaller())
        }
      }
      it.writeDouble(RESPONSE_FIELDS[3], height)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null),
          ResponseField.forList("friends", "friends", null, true, null),
          ResponseField.forDouble("height", "height", null, true, null)
          )

      val POSSIBLE_TYPES: Array<String> = arrayOf("Human")

      operator fun invoke(reader: ResponseReader): AsHuman {
        val __typename = reader.readString(RESPONSE_FIELDS[0])
        val name = reader.readString(RESPONSE_FIELDS[1])
        val friends = reader.readList<Friend>(RESPONSE_FIELDS[2]) {
          it.readObject<Friend> { reader ->
            Friend(reader)
          }

        }
        val height = reader.readDouble(RESPONSE_FIELDS[3])
        return AsHuman(
          __typename = __typename,
          name = name,
          friends = friends,
          height = height
        )
      }
    }
  }

  data class Friend2(
    val __typename: String,
    /**
     * The name of the character
     */
    val name: String
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeString(RESPONSE_FIELDS[0], __typename)
      it.writeString(RESPONSE_FIELDS[1], name)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): Friend2 {
        val __typename = reader.readString(RESPONSE_FIELDS[0])
        val name = reader.readString(RESPONSE_FIELDS[1])
        return Friend2(
          __typename = __typename,
          name = name
        )
      }
    }
  }

  data class HeroDetailQuery1(
    val __typename: String,
    /**
     * The name of the character
     */
    val name: String,
    /**
     * The friends of the character, or an empty list if they have none
     */
    val friends: List<Friend2?>?,
    val inlineFragment: HeroDetailQueryCharacter?
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeString(RESPONSE_FIELDS[0], __typename)
      it.writeString(RESPONSE_FIELDS[1], name)
      it.writeList(RESPONSE_FIELDS[2], friends) { value, listItemWriter ->
        value?.forEach { value ->
          listItemWriter.writeObject(value?.marshaller())
        }
      }
      it.writeObject(RESPONSE_FIELDS[3], inlineFragment?.marshaller())
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("name", "name", null, false, null),
          ResponseField.forList("friends", "friends", null, true, null),
          ResponseField.forInlineFragment("__typename", "__typename", listOf("Human"))
          )

      operator fun invoke(reader: ResponseReader): HeroDetailQuery1 {
        val __typename = reader.readString(RESPONSE_FIELDS[0])
        val name = reader.readString(RESPONSE_FIELDS[1])
        val friends = reader.readList<Friend2>(RESPONSE_FIELDS[2]) {
          it.readObject<Friend2> { reader ->
            Friend2(reader)
          }

        }
        val inlineFragment = reader.readConditional(RESPONSE_FIELDS[3]) { conditionalType, reader ->
          when(conditionalType) {
            in AsHuman.POSSIBLE_TYPES -> AsHuman(reader)
            else -> null
          }
        }

        return HeroDetailQuery1(
          __typename = __typename,
          name = name,
          friends = friends,
          inlineFragment = inlineFragment
        )
      }
    }
  }

  data class Data(
    val heroDetailQuery: HeroDetailQuery1?
  ) : Operation.Data {
    override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller {
      it.writeObject(RESPONSE_FIELDS[0], heroDetailQuery?.marshaller())
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forObject("heroDetailQuery", "heroDetailQuery", null, true, null)
          )

      operator fun invoke(reader: ResponseReader): Data {
        val heroDetailQuery = reader.readObject<HeroDetailQuery1>(RESPONSE_FIELDS[0]) { reader ->
          HeroDetailQuery1(reader)
        }

        return Data(
          heroDetailQuery = heroDetailQuery
        )
      }
    }
  }

  companion object {
    const val OPERATION_ID: String =
        "11473383397766137d7923128dd8cd6f27fcab32df9d9c091f08cf12a893a556"

    val QUERY_DOCUMENT: String = QueryDocumentMinifier.minify(
          """
          |query HeroDetailQuery {
          |  heroDetailQuery {
          |    __typename
          |    name
          |    friends {
          |      __typename
          |      name
          |    }
          |    ... on Human {
          |      height
          |      friends {
          |        __typename
          |        appearsIn
          |        friends {
          |          __typename
          |          ...HeroDetails
          |        }
          |      }
          |    }
          |  }
          |}
          |fragment HeroDetails on Character {
          |  __typename
          |  name
          |  friendsConnection {
          |    __typename
          |    totalCount
          |    edges {
          |      __typename
          |      node {
          |        __typename
          |        name
          |      }
          |    }
          |  }
          |}
          """.trimMargin()
        )

    val OPERATION_NAME: OperationName = OperationName { "HeroDetailQuery" }
  }
}
