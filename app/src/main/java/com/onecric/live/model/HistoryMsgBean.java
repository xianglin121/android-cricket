package com.onecric.live.model;

import java.util.List;

public class HistoryMsgBean {
    private String actionStatus;
    private Long errorCode;
    private String errorInfo;
    private String groupId;
    private Long isFinished;
    private List<RspMsgListDTO> rspMsgList;

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Long isFinished) {
        this.isFinished = isFinished;
    }

    public List<RspMsgListDTO> getRspMsgList() {
        return rspMsgList;
    }

    public void setRspMsgList(List<RspMsgListDTO> rspMsgList) {
        this.rspMsgList = rspMsgList;
    }

    public static class RspMsgListDTO {
        private String cloudCustomData;
        private String fromAccount;
        private Long isPlaceMsg;
        private List<MsgBodyDTO> msgBody;
        private Long msgPriority;
        private Long msgRandom;
        private Long msgSeq;
        private Long msgTimeStamp;
        private Long msgVersion;
        private Long isSystemMsg;

        public String getCloudCustomData() {
            return cloudCustomData;
        }

        public void setCloudCustomData(String cloudCustomData) {
            this.cloudCustomData = cloudCustomData;
        }

        public String getFromAccount() {
            return fromAccount;
        }

        public void setFromAccount(String fromAccount) {
            this.fromAccount = fromAccount;
        }

        public Long getIsPlaceMsg() {
            return isPlaceMsg;
        }

        public void setIsPlaceMsg(Long isPlaceMsg) {
            this.isPlaceMsg = isPlaceMsg;
        }

        public List<MsgBodyDTO> getMsgBody() {
            return msgBody;
        }

        public void setMsgBody(List<MsgBodyDTO> msgBody) {
            this.msgBody = msgBody;
        }

        public Long getMsgPriority() {
            return msgPriority;
        }

        public void setMsgPriority(Long msgPriority) {
            this.msgPriority = msgPriority;
        }

        public Long getMsgRandom() {
            return msgRandom;
        }

        public void setMsgRandom(Long msgRandom) {
            this.msgRandom = msgRandom;
        }

        public Long getMsgSeq() {
            return msgSeq;
        }

        public void setMsgSeq(Long msgSeq) {
            this.msgSeq = msgSeq;
        }

        public Long getMsgTimeStamp() {
            return msgTimeStamp;
        }

        public void setMsgTimeStamp(Long msgTimeStamp) {
            this.msgTimeStamp = msgTimeStamp;
        }

        public Long getMsgVersion() {
            return msgVersion;
        }

        public void setMsgVersion(Long msgVersion) {
            this.msgVersion = msgVersion;
        }

        public Long getIsSystemMsg() {
            return isSystemMsg;
        }

        public void setIsSystemMsg(Long isSystemMsg) {
            this.isSystemMsg = isSystemMsg;
        }

        public static class MsgBodyDTO {
            private RspMsgListDTO.MsgBodyDTO.MsgContentDTO msgContent;
            private String msgType;

            public MsgContentDTO getMsgContent() {
                return msgContent;
            }

            public void setMsgContent(MsgContentDTO msgContent) {
                this.msgContent = msgContent;
            }

            public String getMsgType() {
                return msgType;
            }

            public void setMsgType(String msgType) {
                this.msgType = msgType;
            }

            public static class MsgContentDTO {
                private String data;
                private String desc;
                private String ext;
                private String sound;

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getExt() {
                    return ext;
                }

                public void setExt(String ext) {
                    this.ext = ext;
                }

                public String getSound() {
                    return sound;
                }

                public void setSound(String sound) {
                    this.sound = sound;
                }
            }
        }
    }

    public static class DataDTO {
        private HistoryMsgBean.DataDTO.NormalDTO normal;
        private Long type;

        public HistoryMsgBean.DataDTO.NormalDTO getNormal() {
            return normal;
        }

        public void setNormal(HistoryMsgBean.DataDTO.NormalDTO normal) {
            this.normal = normal;
        }

        public Long getType() {
            return type;
        }

        public void setType(Long type) {
            this.type = type;
        }

        public static class NormalDTO {
            private String expIcon;
            private Long isXCBarrage;
            private Long isGuard;
            private Long isRoom;
            private String text;
            private Long xcBarrageType;

            public String getExpIcon() {
                return expIcon;
            }

            public void setExpIcon(String expIcon) {
                this.expIcon = expIcon;
            }

            public Long getIsXCBarrage() {
                return isXCBarrage;
            }

            public void setIsXCBarrage(Long isXCBarrage) {
                this.isXCBarrage = isXCBarrage;
            }

            public Long getIsGuard() {
                return isGuard;
            }

            public void setIsGuard(Long isGuard) {
                this.isGuard = isGuard;
            }

            public Long getIsRoom() {
                return isRoom;
            }

            public void setIsRoom(Long isRoom) {
                this.isRoom = isRoom;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public Long getXcBarrageType() {
                return xcBarrageType;
            }

            public void setXcBarrageType(Long xcBarrageType) {
                this.xcBarrageType = xcBarrageType;
            }
        }
}
}
