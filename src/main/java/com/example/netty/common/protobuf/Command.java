// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Command.proto

package com.example.netty.common.protobuf;

public final class Command {
  private Command() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * <pre>
   **
   * 指令类型
   * </pre>
   *
   * Protobuf enum {@code CommandType}
   */
  public enum CommandType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <pre>
     **
     * 验证
     * </pre>
     *
     * <code>AUTH = 1;</code>
     */
    AUTH(1),
    /**
     * <pre>
     **
     * ping
     * </pre>
     *
     * <code>PING = 2;</code>
     */
    PING(2),
    /**
     * <pre>
     **
     * pong
     * </pre>
     *
     * <code>PONG = 3;</code>
     */
    PONG(3),
    /**
     * <pre>
     **
     * 上传数据
     * </pre>
     *
     * <code>UPLOAD_DATA = 4;</code>
     */
    UPLOAD_DATA(4),
    /**
     * <pre>
     **
     * 推送数据
     * </pre>
     *
     * <code>PUSH_DATA = 5;</code>
     */
    PUSH_DATA(5),
    /**
     * <pre>
     **
     * 验证返回
     * </pre>
     *
     * <code>AUTH_BACK = 11;</code>
     */
    AUTH_BACK(11),
    /**
     * <code>UPLOAD_DATA_BACK = 14;</code>
     */
    UPLOAD_DATA_BACK(14),
    /**
     * <code>PUSH_DATA_BACK = 15;</code>
     */
    PUSH_DATA_BACK(15),
    ;

    /**
     * <pre>
     **
     * 验证
     * </pre>
     *
     * <code>AUTH = 1;</code>
     */
    public static final int AUTH_VALUE = 1;
    /**
     * <pre>
     **
     * ping
     * </pre>
     *
     * <code>PING = 2;</code>
     */
    public static final int PING_VALUE = 2;
    /**
     * <pre>
     **
     * pong
     * </pre>
     *
     * <code>PONG = 3;</code>
     */
    public static final int PONG_VALUE = 3;
    /**
     * <pre>
     **
     * 上传数据
     * </pre>
     *
     * <code>UPLOAD_DATA = 4;</code>
     */
    public static final int UPLOAD_DATA_VALUE = 4;
    /**
     * <pre>
     **
     * 推送数据
     * </pre>
     *
     * <code>PUSH_DATA = 5;</code>
     */
    public static final int PUSH_DATA_VALUE = 5;
    /**
     * <pre>
     **
     * 验证返回
     * </pre>
     *
     * <code>AUTH_BACK = 11;</code>
     */
    public static final int AUTH_BACK_VALUE = 11;
    /**
     * <code>UPLOAD_DATA_BACK = 14;</code>
     */
    public static final int UPLOAD_DATA_BACK_VALUE = 14;
    /**
     * <code>PUSH_DATA_BACK = 15;</code>
     */
    public static final int PUSH_DATA_BACK_VALUE = 15;


    public final int getNumber() {
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static CommandType valueOf(int value) {
      return forNumber(value);
    }

    public static CommandType forNumber(int value) {
      switch (value) {
        case 1: return AUTH;
        case 2: return PING;
        case 3: return PONG;
        case 4: return UPLOAD_DATA;
        case 5: return PUSH_DATA;
        case 11: return AUTH_BACK;
        case 14: return UPLOAD_DATA_BACK;
        case 15: return PUSH_DATA_BACK;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<CommandType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        CommandType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<CommandType>() {
            public CommandType findValueByNumber(int number) {
              return CommandType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.example.netty.common.protobuf.Command.getDescriptor().getEnumTypes().get(0);
    }

    private static final CommandType[] VALUES = values();

    public static CommandType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private CommandType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:CommandType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rCommand.proto\032\rCommand.proto*\204\001\n\013Comma" +
      "ndType\022\010\n\004AUTH\020\001\022\010\n\004PING\020\002\022\010\n\004PONG\020\003\022\017\n\013" +
      "UPLOAD_DATA\020\004\022\r\n\tPUSH_DATA\020\005\022\r\n\tAUTH_BAC" +
      "K\020\013\022\024\n\020UPLOAD_DATA_BACK\020\016\022\022\n\016PUSH_DATA_B" +
      "ACK\020\017B$\n\031com.netty.common.protobufB\007Comm" +
      "and"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.example.netty.common.protobuf.Command.getDescriptor(),
        }, assigner);
    com.example.netty.common.protobuf.Command.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
