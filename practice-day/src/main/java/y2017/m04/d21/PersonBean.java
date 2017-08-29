// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Person.proto

package y2017.m04.d21;

public final class PersonBean {
  private PersonBean() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface PersonOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional int32 age = 1 [default = 1];
    /**
     * <code>optional int32 age = 1 [default = 1];</code>
     */
    boolean hasAge();
    /**
     * <code>optional int32 age = 1 [default = 1];</code>
     */
    int getAge();

    // optional string name = 2 [default = "kevin"];
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    boolean hasName();
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    String getName();
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    // repeated int32 test_num = 3;
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    java.util.List<Integer> getTestNumList();
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    int getTestNumCount();
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    int getTestNum(int index);
  }
  /**
   * Protobuf type {@code y2017.m04.d21.Person}
   */
  public static final class Person extends
      com.google.protobuf.GeneratedMessage
      implements PersonOrBuilder {
    // Use Person.newBuilder() to construct.
    private Person(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Person(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Person defaultInstance;
    public static Person getDefaultInstance() {
      return defaultInstance;
    }

    public Person getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Person(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              age_ = input.readInt32();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              name_ = input.readBytes();
              break;
            }
            case 24: {
              if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
                testNum_ = new java.util.ArrayList<Integer>();
                mutable_bitField0_ |= 0x00000004;
              }
              testNum_.add(input.readInt32());
              break;
            }
            case 26: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000004) == 0x00000004) && input.getBytesUntilLimit() > 0) {
                testNum_ = new java.util.ArrayList<Integer>();
                mutable_bitField0_ |= 0x00000004;
              }
              while (input.getBytesUntilLimit() > 0) {
                testNum_.add(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
          testNum_ = java.util.Collections.unmodifiableList(testNum_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PersonBean.internal_static_y2017_m04_d21_Person_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PersonBean.internal_static_y2017_m04_d21_Person_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Person.class, Builder.class);
    }

    public static com.google.protobuf.Parser<Person> PARSER =
        new com.google.protobuf.AbstractParser<Person>() {
      public Person parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Person(input, extensionRegistry);
      }
    };

    @Override
    public com.google.protobuf.Parser<Person> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional int32 age = 1 [default = 1];
    public static final int AGE_FIELD_NUMBER = 1;
    private int age_;
    /**
     * <code>optional int32 age = 1 [default = 1];</code>
     */
    public boolean hasAge() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional int32 age = 1 [default = 1];</code>
     */
    public int getAge() {
      return age_;
    }

    // optional string name = 2 [default = "kevin"];
    public static final int NAME_FIELD_NUMBER = 2;
    private Object name_;
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    public String getName() {
      Object ref = name_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          name_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string name = 2 [default = "kevin"];</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // repeated int32 test_num = 3;
    public static final int TEST_NUM_FIELD_NUMBER = 3;
    private java.util.List<Integer> testNum_;
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    public java.util.List<Integer>
        getTestNumList() {
      return testNum_;
    }
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    public int getTestNumCount() {
      return testNum_.size();
    }
    /**
     * <code>repeated int32 test_num = 3;</code>
     */
    public int getTestNum(int index) {
      return testNum_.get(index);
    }

    private void initFields() {
      age_ = 1;
      name_ = "kevin";
      testNum_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, age_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getNameBytes());
      }
      for (int i = 0; i < testNum_.size(); i++) {
        output.writeInt32(3, testNum_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, age_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getNameBytes());
      }
      {
        int dataSize = 0;
        for (int i = 0; i < testNum_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(testNum_.get(i));
        }
        size += dataSize;
        size += 1 * getTestNumList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    protected Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static Person parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Person parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Person parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Person parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Person parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Person parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static Person parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static Person parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static Person parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Person parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(Person prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code y2017.m04.d21.Person}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements PersonOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return PersonBean.internal_static_y2017_m04_d21_Person_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return PersonBean.internal_static_y2017_m04_d21_Person_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Person.class, Builder.class);
      }

      // Construct using y2017.m04.d21.PersonBean.Person.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        age_ = 1;
        bitField0_ = (bitField0_ & ~0x00000001);
        name_ = "kevin";
        bitField0_ = (bitField0_ & ~0x00000002);
        testNum_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return PersonBean.internal_static_y2017_m04_d21_Person_descriptor;
      }

      public Person getDefaultInstanceForType() {
        return Person.getDefaultInstance();
      }

      public Person build() {
        Person result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Person buildPartial() {
        Person result = new Person(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.age_ = age_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.name_ = name_;
        if (((bitField0_ & 0x00000004) == 0x00000004)) {
          testNum_ = java.util.Collections.unmodifiableList(testNum_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.testNum_ = testNum_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Person) {
          return mergeFrom((Person)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Person other) {
        if (other == Person.getDefaultInstance()) return this;
        if (other.hasAge()) {
          setAge(other.getAge());
        }
        if (other.hasName()) {
          bitField0_ |= 0x00000002;
          name_ = other.name_;
          onChanged();
        }
        if (!other.testNum_.isEmpty()) {
          if (testNum_.isEmpty()) {
            testNum_ = other.testNum_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureTestNumIsMutable();
            testNum_.addAll(other.testNum_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Person parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Person) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional int32 age = 1 [default = 1];
      private int age_ = 1;
      /**
       * <code>optional int32 age = 1 [default = 1];</code>
       */
      public boolean hasAge() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional int32 age = 1 [default = 1];</code>
       */
      public int getAge() {
        return age_;
      }
      /**
       * <code>optional int32 age = 1 [default = 1];</code>
       */
      public Builder setAge(int value) {
        bitField0_ |= 0x00000001;
        age_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 age = 1 [default = 1];</code>
       */
      public Builder clearAge() {
        bitField0_ = (bitField0_ & ~0x00000001);
        age_ = 1;
        onChanged();
        return this;
      }

      // optional string name = 2 [default = "kevin"];
      private Object name_ = "kevin";
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public boolean hasName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public String getName() {
        Object ref = name_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public Builder setName(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>optional string name = 2 [default = "kevin"];</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        name_ = value;
        onChanged();
        return this;
      }

      // repeated int32 test_num = 3;
      private java.util.List<Integer> testNum_ = java.util.Collections.emptyList();
      private void ensureTestNumIsMutable() {
        if (!((bitField0_ & 0x00000004) == 0x00000004)) {
          testNum_ = new java.util.ArrayList<Integer>(testNum_);
          bitField0_ |= 0x00000004;
         }
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public java.util.List<Integer>
          getTestNumList() {
        return java.util.Collections.unmodifiableList(testNum_);
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public int getTestNumCount() {
        return testNum_.size();
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public int getTestNum(int index) {
        return testNum_.get(index);
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public Builder setTestNum(
          int index, int value) {
        ensureTestNumIsMutable();
        testNum_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public Builder addTestNum(int value) {
        ensureTestNumIsMutable();
        testNum_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public Builder addAllTestNum(
          Iterable<? extends Integer> values) {
        ensureTestNumIsMutable();
        super.addAll(values, testNum_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 test_num = 3;</code>
       */
      public Builder clearTestNum() {
        testNum_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:y2017.m04.d21.Person)
    }

    static {
      defaultInstance = new Person(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:y2017.m04.d21.Person)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_y2017_m04_d21_Person_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_y2017_m04_d21_Person_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\014Person.proto\022\ry2017.m04.d21\"?\n\006Person\022" +
      "\016\n\003age\030\001 \001(\005:\0011\022\023\n\004name\030\002 \001(\t:\005kevin\022\020\n\010" +
      "test_num\030\003 \003(\005B\035\n\ry2017.m04.d21B\nPersonB" +
      "eanH\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_y2017_m04_d21_Person_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_y2017_m04_d21_Person_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_y2017_m04_d21_Person_descriptor,
              new String[] { "Age", "Name", "TestNum", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
